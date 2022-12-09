package com.example.repository;

public class Message {
    private String Id;
    private String sender;
    private String message;

    public Message(String id, String sender, String message) {
        Id = id;
        this.sender = sender;
        this.message = message;
    }

    public Message() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
