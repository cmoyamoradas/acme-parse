package org.acme.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.beans.Binary;
import org.acme.beans.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

/**
 * Class of utilities for Collection processing
 */
public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static Results readFile (String inputPath){
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(inputPath));

            //create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

            //convert json string to object
            return objectMapper.readValue(jsonData, Results.class);

        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    public static void orderBinariesByDownloadedSize(List<Binary> binaries) {
        Collections.sort(binaries, new Comparator<Binary>() {
            public int compare(Binary b1, Binary b2) {
                double delta = b1.getTotalDownloadedMB() - b2.getTotalDownloadedMB();
                if (delta > 0.00001) return -1;
                if (delta < -0.00001) return 1;
                return 0;
            }
        });
    }

    public static void orderBinariesByDownloads(List<Binary> binaries) {
        Collections.sort(binaries, new Comparator<Binary>() {
            public int compare(Binary b1, Binary b2) {
                double delta = b1.getStats().get(0).getDownloads() - b2.getStats().get(0).getDownloads();
                if (delta > 0.00001) return 1;
                if (delta < -0.00001) return -1;
                return 0;
            }
        });
    }

    public static Map<String, List<Binary>> convertListIntoMap(List<Binary> list){
        return list.stream()
                .collect(Collectors.groupingBy(Binary::getName,Collectors.toList()));
    }

    public static Map<String,Integer> orderEntriesByValue(Map<String,Integer> unorderedMap){
        return unorderedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public static Map<Integer,Long> extractFirstNEntriesOrderedByValue (Map<Integer,Long> unorderedMap, int limit){
        return unorderedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
    public static void writeToCsv(List<Binary> list, String header, String output, int limit) {
        try {
            PrintWriter writer = new PrintWriter(output);
            writer.println(header);

            int i = 0;
            for (Binary bin : list) {
                if (limit >0 && i>limit){
                    break;
                }
                writer.println(bin.toString());
                i++;
            }
            writer.close();
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

}
