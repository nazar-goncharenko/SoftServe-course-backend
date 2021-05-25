package com.softserve.app.models;

import com.softserve.app.dto.ArticleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_path")
    private String imagePath;

    /* FE: alt */
    @Column(name = "alt_img_name")
    private String altImgName;

    /* FE: article headline */
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "caption")
    private String caption;

    /* FE: content */
    @Column(name = "description", nullable = false)
    private String description;

    /* FE: show comments toggle */
    @Column(name = "show_comments")
    private Boolean showComments;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private SportCategory category;

    public ArticleDTO ofDTO() {
        return ArticleDTO.builder()
                .id(this.id)
                .imagePath(this.imagePath)
                .altImgName(this.altImgName)
                .title(this.title)
                .caption(this.caption)
                .description(this.description)
                .showComments(this.showComments)
                .comments(new ArrayList<>(this.comments))
                .category(this.category)
                .build();
    }

}
