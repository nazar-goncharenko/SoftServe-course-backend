package com.softserve.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softserve.app.models.Survey;
import com.softserve.app.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO implements Serializable {

    private Long id;

    private String username;

    private String email;

    private String password; //old one
    private String new_pass;
    private String new_pass_2;

    private String photoUrl;

    private String token;

    private User.Role role;

    private List<Survey> userSurveys = new ArrayList<>();
    private List<SportCategoryDTO> favourites = new ArrayList<>();

    public User ofEntity() {
        return User.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .password(this.new_pass_2)
                .photoUrl(this.photoUrl)
                .role(this.role)
                .favourites(new HashSet<>(
                        this.favourites.stream()
                                .map(SportCategoryDTO::ofEntity)
                                .collect(Collectors.toList())
                ))
                .userSurveys(new HashSet<>(this.userSurveys))
                .build();
    }
}
