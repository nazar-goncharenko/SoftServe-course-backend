package com.softserve.app.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    User author;

    @OneToMany(mappedBy = "survey",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CheckBox> checkBoxes;

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

    public Set<CheckBox> getOptions() {
        return checkBoxes;
    }

    public void setOptions(Set<CheckBox> checkBoxes) {
        this.checkBoxes = checkBoxes;
    }

    public Boolean getShown() {
        return isShown;
    }

    public void setShown(Boolean shown) {
        isShown = shown;
    }
}
