package com.softserve.app.dto;

import com.softserve.app.models.Banner;
import com.softserve.app.models.Comment;
import com.softserve.app.models.SportCategory;
import com.softserve.app.models.Survey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    private Set<Survey> userSurveys = new HashSet<>();
    private Set<Banner> userBanners = new HashSet<>();
    private Set<Comment> userComments = new HashSet<>();
    private Set<SportCategory> favourites = new HashSet<>();
}
