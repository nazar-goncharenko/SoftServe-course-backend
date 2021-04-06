package com.softserve.app.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder(toBuilder=true)
public class UserDTO implements Serializable {
    private String email;
    private String password;
    private String username;
    private String photoUrl;
}
