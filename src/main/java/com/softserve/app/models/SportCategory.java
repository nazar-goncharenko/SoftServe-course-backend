package com.softserve.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sport_category")
public class SportCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //////////////////////////


    // я parent, і маю багатьох children
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SportCategory> children = new HashSet<>();


    // я один з багатьох children і маю одного parent
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private SportCategory parent;


    //////////////////////////

    @Column(name = "name", nullable = false)
    private String  name;

    @Column(name = "description")
    private String  description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Article> articles = new HashSet<>();

    @ManyToMany(mappedBy = "favourites",fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Set<User> favouriteBy = new HashSet<>();

    public SportCategory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //////////////////////////


    public Set<SportCategory> getChildren() {
        return children;
    }

    public void setChildren(Set<SportCategory> children) {
        this.children = children;
    }

    public SportCategory getParent_id() {
        return this.parent;
    }

    public void setParent_id(SportCategory sportCategory) {
        this.parent = sportCategory;
    }

    //////////////////////////


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<User> getFavouriteBy() {
        return favouriteBy;
    }

    public void setFavouriteBy(Set<User> favouriteBy) {
        this.favouriteBy = favouriteBy;
    }


}
