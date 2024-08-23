package org.acme.beans;

import java.util.List;

public class Stats {

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    private List<Entry> entries;

}
