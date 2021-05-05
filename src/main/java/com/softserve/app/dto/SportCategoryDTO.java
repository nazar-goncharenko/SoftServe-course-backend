package com.softserve.app.dto;


import com.softserve.app.models.Article;
import com.softserve.app.models.SportCategory;
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

    private boolean isPredefined;

    private boolean showBanners;

    private List<Article> articles = new ArrayList<>();

    private List<User> favourite = new ArrayList<>();

    public SportCategory ofEntity(){
        return SportCategory.builder()
                .articles(new HashSet<>(this.articles))
                .banners(null)
                .description(this.description)
                .favourite(new HashSet<>(this.favourite))
                .id(this.id)
                .isPredefined(this.isPredefined)
                .name(this.name)
                .parent(null)
                .showBanners(this.showBanners)
                .build();
    }
}
