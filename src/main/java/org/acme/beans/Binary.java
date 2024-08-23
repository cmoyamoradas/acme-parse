package org.acme.beans;

import org.acme.commands.GetListOfMostDownloadedLayers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Binary {
    private static final Logger log = LoggerFactory.getLogger(Binary.class);


    public final int KBFACTOR = 1024;
    public final double MBFACTOR = KBFACTOR*KBFACTOR;
    public final double GBFACTOR = MBFACTOR*KBFACTOR;
    public final double TBFACTOR = GBFACTOR*KBFACTOR;

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
    public List<Entry> getStats() {
        return stats;
    }

    public void setStats(List<Entry> stats) {
        this.stats = stats;
    }

    private String repo;
    private String path;
    private String name;
    private long size;
    private List<Entry> stats;

    public double getTotalDownloadedGB () {
        int downloads = 0;
        for(Entry entry : stats){
            downloads = downloads + entry.getDownloads();
        }
        return downloads*size/GBFACTOR;
    }
    public double getTotalDownloadedTB () {
        int downloads = 0;
        for(Entry entry : stats){
            downloads = downloads + entry.getDownloads();
        }
        return downloads*size/TBFACTOR;
    }
    public double getTotalDownloadedMB () {
        int downloads = 0;
        for(Entry entry : stats){
            downloads = downloads + entry.getDownloads();
        }
        return downloads*size/MBFACTOR;
    }

    @Override
    public String toString(){
        StringBuilder dataBuilder = new StringBuilder();
        appendFieldValue(dataBuilder, path);
        appendFieldValue(dataBuilder, name);
        appendFieldValue(dataBuilder, String.valueOf(size));
        appendFieldValue(dataBuilder, String.valueOf(stats.get(0).getDownloads()));
        appendFieldValue(dataBuilder, String.valueOf(this.getTotalDownloadedGB()));
        return dataBuilder.toString();
    }

    private void appendFieldValue(StringBuilder dataBuilder, String fieldValue) {
        if(fieldValue != null) {
            dataBuilder.append(fieldValue).append(",");
        } else {
            dataBuilder.append("").append(",");
        }
    }
}
