package com.softserve.app.request;

import java.io.Serializable;


public class LoginRequest implements Serializable {

    private String email;
    private String password;

    public LoginRequest() {

    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}