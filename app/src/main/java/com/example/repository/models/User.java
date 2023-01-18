package com.example.repository.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String surname;
    public String name;
    public boolean chatWithAdmin;

    public User(String surname,String name,boolean chatWithAdmin) {
        this.surname = surname;
        this.name=name;
        this.chatWithAdmin=chatWithAdmin;
    }
    public User(){

    }
}
