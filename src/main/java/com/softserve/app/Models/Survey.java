package com.softserve.app.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    User author;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Option> options;

    @Column(name = "isShown", nullable = false)
    private Boolean isShown = false;

    public Survey() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public Boolean getShown() {
        return isShown;
    }

    public void setShown(Boolean shown) {
        isShown = shown;
    }
}
