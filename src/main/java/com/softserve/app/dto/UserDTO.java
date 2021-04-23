package com.softserve.app.dto;

import com.softserve.app.models.Banner;
import com.softserve.app.models.Comment;
import com.softserve.app.models.Survey;
import com.softserve.app.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDTO implements Serializable {

    private Long id;

    private String username;

    private String email;

    private String password;

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
                //.favourites(SportCategoryDtoHashSetFavourites) //overflow
                .userComments((Set<Comment>) this.userComments)
                .build();
    }
}
