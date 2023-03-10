package com.example.repository.models;

public class Message {
    private String sender;
    private String text;
    private String linkAvatar;


    public Message(String sender, String text,String linkAvatar) {
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
