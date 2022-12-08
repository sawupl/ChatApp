package com.example.repository;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String surname;
    public String name;

    public User(String surname,String name) {
        this.surname = surname;
        this.name=name;
    }
    public User(){

    }
}
