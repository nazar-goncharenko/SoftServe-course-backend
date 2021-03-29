package com.softserve.app.models;

import javax.persistence.*;


@Entity
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    User admin;

    @Column(name = "title", nullable = false)
    private String  title;

    @Column(name = "link", nullable = false)
    private String  link;

    public enum Priority {
        LOW, MID, HIGH;
    }
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "isShown", nullable = false)
    private Boolean isShown;

    public Banner() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Boolean getShown() {
        return isShown;
    }

    public void setShown(Boolean shown) {
        isShown = shown;
    }
}
