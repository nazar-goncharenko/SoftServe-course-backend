package com.softserve.app.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    User author;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    Article article;

    @Column(name = "comment", nullable = false)
    private String  comment;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    @Column(name = "likes", nullable = false)
    private Long likes;

    @Column(name = "dislikes", nullable = false)
    private Long dislikes;

    public Comment() {
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Long getLikes() {
        return likes;
    }

    // addlikes: likes += 1            ?????
    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getDislikes() {
        return dislikes;
    }

    // addDislike: dislikes += 1            ?????
    public void setDislikes(Long dislikes) {
        this.dislikes = dislikes;
    }
}
