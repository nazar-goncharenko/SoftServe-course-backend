package com.softserve.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_path")
    private String imagePath;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private SportCategory category;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<History> histories;

    public ArticleDTO ofDTO(){
        return ArticleDTO.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .category(this.category)
//                .comments(comments.stream()
//                        .map(Comment::ofDTO)
//                        .collect(Collectors.toSet()))
                .date(date)
//                .histories(histories.stream()
//                        .map(History::ofDTO)
//                        .collect(Collectors.toSet()))
                .imagePath(imagePath)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) && Objects.equals(title, article.title) && Objects.equals(description, article.description) && Objects.equals(imagePath, article.imagePath) && Objects.equals(date, article.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, imagePath, date);
    }
}
