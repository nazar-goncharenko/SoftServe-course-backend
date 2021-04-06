package com.softserve.app.dto;


import com.softserve.app.models.Article;
import com.softserve.app.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SportCategoryDTO implements Serializable {

    private Long id;

    private List<SportCategoryDTO> children = new ArrayList<>();

    private String name;

    private String description;

    private List<Article> articles = new ArrayList<>();

    private List<User> favourite = new ArrayList<>();
}
