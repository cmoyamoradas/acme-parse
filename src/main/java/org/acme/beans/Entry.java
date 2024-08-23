package org.acme.beans;

import java.util.Date;

public class Entry {
    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public Date getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(Date downloaded) {
        this.downloaded = downloaded;
    }

    public String getDownloaded_by() {
        return downloaded_by;
    }

    public void setDownloaded_by(String downloadedBy) {
        this.downloaded_by = downloadedBy;
    }

    private int downloads;
    private Date downloaded;
    private String downloaded_by;
}
