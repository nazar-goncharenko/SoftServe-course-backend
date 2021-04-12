package com.softserve.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softserve.app.dto.SportCategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "sport_category")
public class SportCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // я parent, і маю багатьох children
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private final List<SportCategory> children = new ArrayList<>();


    // я один з багатьох children і маю одного parent
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private SportCategory parent;


    //////////////////////////

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "isPredefined")
    private boolean isPredefined;

    @Column(name = "showBanners")
    private boolean showBanners;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Article> articles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Banner> banners = new HashSet<>();

    @ManyToMany(mappedBy = "favourites", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Set<User> favourite = new HashSet<>();

    public SportCategoryDTO ofDTO(){
        return SportCategoryDTO.builder()
                .id(this.id)
                .name(this.name)
                .children(this.children.stream()
                        .map(SportCategory::ofDTO)
                        .collect(Collectors.toList())
                )
                .isPredefined(this.isPredefined)
                .showBanners(this.showBanners)
                .articles(new ArrayList<>(this.articles))//add stream map ArticleDTO
                .favourite(new ArrayList<>(this.favourite))//add stream map UserDTO
                .description(this.description)
                .build();
    }
}
