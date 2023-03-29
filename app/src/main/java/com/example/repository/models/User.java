package com.example.repository.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String name;
    public String login;

    public User(String name,String login) {
        this.name=name;
        this.login = login;
    }
    public User(){

    }
}
