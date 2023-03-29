package com.example.repository.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String login;
    public String name;

    public User(String login,String name) {
        this.login = login;
        this.name=name;
    }
    public User(){

    }
}
