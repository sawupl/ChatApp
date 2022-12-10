package com.example.repository;

import com.google.firebase.database.ServerValue;

public class Message {
    private long id = System.currentTimeMillis();
    private String sender;
    private String message;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Message(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public Message() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
