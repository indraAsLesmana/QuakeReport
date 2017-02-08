package com.example.android.quakereport.model;

/**
 * Created by indraaguslesmana on 2/8/17.
 */

public class WaChat {

    private boolean has_read;
    private String message;
    private int sender;
    private long sent_at;

    public boolean isHas_read() {
        return has_read;
    }

    public void setHas_read(boolean has_read) {
        this.has_read = has_read;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public long getSent_at() {
        return sent_at;
    }

    public void setSent_at(long sent_at) {
        this.sent_at = sent_at;
    }
}
