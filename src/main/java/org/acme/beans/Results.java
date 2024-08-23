package org.acme.beans;

import java.util.List;

public class Results {
    public List<Binary> getResults() {
        return results;
    }

    public void setResults(List<Binary> results) {
        this.results = results;
    }

    private List<Binary> results;

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    private Range range;
}
