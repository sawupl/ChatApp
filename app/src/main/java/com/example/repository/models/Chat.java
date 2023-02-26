package com.example.repository.models;

public class Chat {
    private String id;
    private String name;
    private long lastUpdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Chat(String id, String name, long lastUpdate){
        this.id = id;
        this.name=name;
        this.lastUpdate = lastUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    //    public int getAvaResource() {
//        return avaResource;
//    }
//
//    public void setAvaResource(int avaResource) {
//        this.avaResource = avaResource;
//    }
}
