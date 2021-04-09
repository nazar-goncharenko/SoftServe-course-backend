package com.softserve.app.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder=true)
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "username", nullable = false)
    private String username;

    @NonNull
    @Column(name = "email", nullable = false)
    private String email;

    @NonNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "photoUrl")
    private String photoUrl;

    private enum Role {
        ADMIN, USER;
    }

    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; // default role: user

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
}
