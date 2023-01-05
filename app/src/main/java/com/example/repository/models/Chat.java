package com.example.repository.models;

public class Chat {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Chat(String id, String name){
        this.id = id;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public int getAvaResource() {
//        return avaResource;
//    }
//
//    public void setAvaResource(int avaResource) {
//        this.avaResource = avaResource;
//    }
}
