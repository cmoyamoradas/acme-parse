package org.acme.commands;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.beans.Binary;
import org.acme.beans.Results;
import org.acme.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Command(name="most-downloaded", description="List of most downloaded layers")
@Component
public class GetListOfMostDownloadedLayers implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(GetListOfMostDownloadedLayers.class);
    /**
     * Property for the Help menu
     */
    @Option(names = { "-h", "--help" }, usageHelp = true, description = "Usage")
    private boolean helpRequested = false;
    /**
     * Path of a file with downloads data
     */
    @Option(names = {"-f", "--file"},
            description = "Provide the downloads data [REQUIRED]",
            required = true)
    String inputPath;

    /**
     * Path to a file to write the output
     */
    @Option(names = {"-o", "--output"},
            description = "Write the output in a file [OPTIONAL]")
    String outputPath;

    @Option(names = {"-l", "--limit"},
            description = "Limit of results [OPTIONAL]. Default: 50")
    int limit;

    /**
     * Finds the greatest number of countries a passenger has been in without being in the UK.
     */
    @Override
    public void run() {
        Results results = Utils.readFile(inputPath);

        Map<String, List<Binary>> map = Utils.convertListIntoMap(results.getResults());

        Map<String,Integer> finalMap = new LinkedHashMap<>();
        for (String key : map.keySet()) {
            List<Binary> binaries = map.get(key);
            int downloads = 0;
            for (Binary bin : binaries){
                downloads = downloads + bin.getStats().get(0).getDownloads();
            }
            finalMap.put(key,downloads);
        }
        finalMap = Utils.orderEntriesByValue(finalMap);

        this.mapToConsole(finalMap,limit<=0?50:limit);

    }
    public void mapToConsole(Map<String,Integer> map, int limit){

        System.out.printf("--------------------------------%n");
        System.out.printf(" Most Downloaded layers         %n");
        System.out.printf(" (number of downloads)          %n");

        System.out.printf("--------------------------------%n");
        System.out.printf("| %-72s | %-9s |%n", "Layer SHA1", "Downloads");
        System.out.printf("--------------------------------%n");

        int i = 0;
        for (String key : map.keySet()) {
            if (limit >0 && i>limit){
                break;
            }
            System.out.printf("| %-72s | %-9s |%n", key, map.get(key));
            i++;
        }
        System.out.printf("--------------------------------%n");

    }
}
