package com.teamtreehouse.ribbit.models;

/**
 * Created by javie on 3/26/2017.
 */

public class Auth {

    private static final Auth instance = new Auth();
    private User currentUser;

    private Auth(){}

    public static Auth getInstance() {

        return instance;
    }

    public void setUser(User user) {

        this.currentUser = user;
    }

    public String getId() {

        return this.currentUser.getId();
    }

    public String getEmail() {

        return this.currentUser.getEmail();
    }

    public String getUsername(){

        return this.currentUser.getUsername();
    }

    public User getUser() {

        return this.currentUser;
    }
}
