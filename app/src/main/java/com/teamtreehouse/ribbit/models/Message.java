package com.teamtreehouse.ribbit.models;

/**
 * Created by javie on 4/8/2017.
 */

public class Message extends Item {

    private String username;
    private String text;
    private long date;

    public Message(){}

    public Message(String username, String text, long date) {

        this.username = username;
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public long getDate() {
        return date;
    }

    public String getUsername() {

        return this.username;
    }
}
