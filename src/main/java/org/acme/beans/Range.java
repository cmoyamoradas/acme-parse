package org.acme.beans;

public class Range {
    private int start_pos;
    private long end_pos;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getEnd_pos() {
        return end_pos;
    }

    public void setEnd_pos(long end_pos) {
        this.end_pos = end_pos;
    }

    public int getStart_pos() {
        return start_pos;
    }

    public void setStart_pos(int start_pos) {
        this.start_pos = start_pos;
    }

    private long total;

}
