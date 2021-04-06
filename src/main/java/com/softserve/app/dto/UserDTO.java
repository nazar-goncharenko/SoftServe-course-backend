package com.softserve.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private String email;

    @JsonIgnore
    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public void setEmail(String email) {
        this.email = email;
    }
}
