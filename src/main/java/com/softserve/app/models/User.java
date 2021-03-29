package com.softserve.app.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    private enum Role {
        ADMIN, USER;
    }
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Survey> userSurveys = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Banner> userBanners = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Comment> userComments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "users_sportCategoties",
            joinColumns = { @JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "sportCategory_id")})
    private Set<SportCategory> favourites = new HashSet<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Survey> getUserSurveys() {
        return userSurveys;
    }

    public void setUserSurveys(Set<Survey> userSurveys) {
        this.userSurveys = userSurveys;
    }

    public Set<Banner> getUserBanners() {
        return userBanners;
    }

    public void setUserBanners(Set<Banner> userBanners) {
        this.userBanners = userBanners;
    }

    public Set<Comment> getUserComments() {
        return userComments;
    }

    public void setUserComments(Set<Comment> userComments) {
        this.userComments = userComments;
    }

    public Set<SportCategory> getFavourites() {
        return favourites;
    }

    public void setFavourites(Set<SportCategory> favourites) {
        this.favourites = favourites;
    }
}
