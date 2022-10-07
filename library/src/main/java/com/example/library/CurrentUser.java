package com.example.library;

public class CurrentUser {



    public String loggedInUser;

    public CurrentUser(String user){
        this.loggedInUser = user;
    }


    public String getCurrentUser(){
        return this.loggedInUser;
    }

    public void setCurrentUser(String user){
        this.loggedInUser = user;
    }
}
