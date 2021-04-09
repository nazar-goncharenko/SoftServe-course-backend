package com.softserve.app.models;

import com.softserve.app.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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

    @Column(name = "photoUrl")
    private String photoUrl;

    public enum Role {
        ADMIN, USER;
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Survey> userSurveys = new HashSet<>();

    @OneToMany(mappedBy = "admin", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Banner> userBanners = new HashSet<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Comment> userComments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "users_sportCategoties",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "sportCategory_id")})
    private Set<SportCategory> favourites = new HashSet<>();


    public UserDTO ofDTO(){
        return UserDTO.builder()
                .email(this.email)
                .id(this.id)
                .favourites(this.favourites.stream()
                        .map(SportCategory::ofDTO)
                        .collect(Collectors.toList()))
                .password(this.password)
                .photoUrl(this.photoUrl)
                .userBanners(new ArrayList<>(this.userBanners))
                .userComments(new ArrayList<>(this.userComments))
                .username(this.username)
                .userSurveys(new ArrayList<>(this.userSurveys))
                .build();
    }
}
