package com.softserve.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softserve.app.models.Comment;
import com.softserve.app.models.Survey;
import com.softserve.app.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private List<Comment> userComments = new ArrayList<>();
    private List<SportCategoryDTO> favourites = new ArrayList<>();

    public User ofEntity(){
        return User.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .password(this.new_pass_2)
                .photoUrl(this.photoUrl)
                /*.favourites(this.favourites.stream()
                        .map(SportCategory::ofDTO)
                        .collect(Collectors.toList()))*/
                .userComments((Set<Comment>) this.userComments)
                .userSurveys((Set<Survey>) this.userSurveys)
                .build();
    }
}
