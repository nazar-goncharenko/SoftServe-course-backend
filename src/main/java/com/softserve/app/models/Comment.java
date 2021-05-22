package com.softserve.app.models;

import lombok.AllArgsConstructor;
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
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private User author;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Article article;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    @Column(name = "likes", nullable = false)
    private Long likes;

    @Column(name = "dislikes", nullable = false)
    private Long dislikes;

//    public CommentDTO ofDTO(){
//        return CommentDTO.builder()
//                .id(this.id)
//                .author(this.author)
//                .article(this.article)
//                .comment(this.comment)
//                .time(this.time)
//                .likes(this.likes)
//                .dislikes(this.dislikes)
//                .build();
//    }
}
