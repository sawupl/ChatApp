package com.example.repository;

public class Message {
    private String name;
    private int avaResource;

    public Message(String name,int avaResource){
        this.name=name;
        this.avaResource=avaResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvaResource() {
        return avaResource;
    }

    public void setAvaResource(int avaResource) {
        this.avaResource = avaResource;
    }
}
