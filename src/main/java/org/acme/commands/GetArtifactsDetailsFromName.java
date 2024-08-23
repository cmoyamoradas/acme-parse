package org.acme.commands;

import org.acme.beans.Binary;
import org.acme.beans.Results;
import org.acme.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine.*;

import java.util.List;
import java.util.Map;

@Command(name="layer-details", description="Docker layer details")
@Component
public class GetArtifactsDetailsFromName implements Runnable{
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

    @Option(names = {"-s", "--sha"},
            description = "Layer sha1 [REQUIRED]",
            required = true)
    String binaryName;

    @Option(names = {"-u", "--unit"},
            description = "Unit MB|GB|TB [OPTIONAL, default GB]")
    String unit;

    @Option(names = {"-l", "--limit"},
            description = "Limit of results [OPTIONAL]. Default: 50")
    int limit;


    @Override
    public void run() {
        Results results = Utils.readFile(inputPath);

        Map<String, List<Binary>> map = Utils.convertListIntoMap(results.getResults());

        List<Binary> bins = map.get(binaryName);

        Utils.orderBinariesByDownloadedSize(bins);

        if (outputPath!=null && outputPath!=""){
            Utils.writeToCsv(bins,"Path,Name,Size,Downloads,Total downloaded", outputPath, limit<=0?50:limit);
        }else {
            listToConsole(bins, limit <= 0 ? 50 : limit);
        }
    }

    public void listToConsole(List<Binary> list, int limit){
        int i = 0;
        for (Binary bin : list) {
            if (limit >0 && i>limit){
                break;
            }
            //if (bin.getStats().get(0).getDownloads()>0)
                log.info(bin.toString());
            i++;
        }
    }

}
