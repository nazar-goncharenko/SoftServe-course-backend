package com.softserve.app.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SportCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //////////////////////////


    // я parent, і маю багатьох children
    @OneToMany(mappedBy = "parent_id",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SportCategory> children = new HashSet<>();


    // я один з багатьох children і маю одного parent
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference
    private SportCategory parent;


    //////////////////////////

    @Column(name = "name", nullable = false)
    private String  name;

    @Column(name = "description")
    private String  description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Article> articles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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


    public Set<Article> getChildren() {
        return children;
    }

    public void setChildren(Set<Article> children) {
        this.children = children;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
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
