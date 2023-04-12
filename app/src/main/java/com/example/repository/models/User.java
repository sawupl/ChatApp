package com.example.repository.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String id;
    public String name;
    public String login;

    public User(String id, String name,String login) {
        this.id = id;
        this.name=name;
        this.login = login;
    }
    public User(String name,String login) {
        this.name=name;
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public User(){

    }
}
