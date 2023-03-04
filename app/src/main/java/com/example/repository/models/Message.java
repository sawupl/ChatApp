package com.example.repository.models;

public class Message {
    private long id;
    private String sender;
    private String text;
    private String linkAvatar;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Message(long id, String sender, String text,String linkAvatar) {
        this.id = id;
        this.sender = sender;
        this.text = text;
        this.linkAvatar=linkAvatar;
    }

    public Message() {
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
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
