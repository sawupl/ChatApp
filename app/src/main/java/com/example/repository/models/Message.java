package com.example.repository.models;

public class Message {
    private long id;
    private String sender;
    private String text;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Message(long id, String sender, String text) {
        this.id = id;
        this.sender = sender;
        this.text = text;
    }

    public Message() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
