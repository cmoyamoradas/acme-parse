package org.acme.commands;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.beans.Binary;
import org.acme.beans.Results;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class GetTotalDownloadedSize {

    public static void main(String[] args) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get("docker-local-stats.txt"));

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        //convert json string to object
        Results bins = objectMapper.readValue(jsonData, Results.class);

        orderBinariesByDownloadedSize(bins.getResults());

        DecimalFormat df = new DecimalFormat("#.#####");
        /*
        for (Binary bin : bins.getResults()) {
            if (bin.getTotalDownloadedMB() > 0) {
                System.out.println(bin.getName() + ":"
                        + bin.getPath() + ":"
                        + df.format(bin.getSize() / bin.GBFACTOR) + "GB:"
                        + bin.getStats().get(0).getDownloads() + ":"
                        + df.format(bin.getTotalDownloadedTB()) + "TB");
            }
        }
        */
        //System.out.println(bins.getResults().stream().filter(o -> o.getSize() > 10).mapToDouble(o -> o.getSize()).sum());

        Map<String,List<Binary>> map = convertListIntoMap(bins.getResults());

        Map<String,Double> finalMap = new LinkedHashMap<>();
        for (String key : map.keySet()) {
            List<Binary> binaries = map.get(key);
            double size = 0.0;
            for (Binary bin : binaries){
                size = size + bin.getTotalDownloadedTB();
            }
            if (size>0){
                finalMap.put(key,size);
            }
        }

        finalMap = orderEntriesByValue(finalMap);
        int i = 0;
        for (String key : finalMap.keySet()){
            i = i+1;
            System.out.println(key + "::" + df.format(finalMap.get(key)));
            List<Binary> binarios = map.get(key);
            int downloads = 0;
            for (Binary b : binarios){
                downloads = downloads + b.getStats().get(0).getDownloads();
                //System.out.println(b.getPath());
            }
            System.out.println(downloads + "::" + binarios.size());
            if (i==10){
                break;
            }
        }

        System.out.println(map.size());

    }

    public static void orderBinariesByDownloadedSize(List<Binary> binaries) {
        Collections.sort(binaries, new Comparator<Binary>() {
            public int compare(Binary b1, Binary b2) {
                double delta = b1.getTotalDownloadedMB() - b2.getTotalDownloadedMB();
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
    public static Map<String, Double> orderEntriesByValue(Map<String,Double> unorderedMap){
        return unorderedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
