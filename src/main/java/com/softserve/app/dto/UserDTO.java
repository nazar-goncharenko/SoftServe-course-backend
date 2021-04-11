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
import java.util.ArrayList;
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

    private List<Survey> userSurveys = new ArrayList<>();
    private List<Banner> userBanners = new ArrayList<>();
    private List<Comment> userComments = new ArrayList<>();
    private List<SportCategoryDTO> favourites = new ArrayList<>();
}
