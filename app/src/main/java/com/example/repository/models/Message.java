package com.example.repository.models;

public class Message {
    private String id;
    private String sender;
    private String text;

    public Message(String id, String sender, String text) {
        this.id = id;
        this.sender = sender;
        this.text = text;
    }

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
