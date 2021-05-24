package com.softserve.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softserve.app.models.Comment;
import com.softserve.app.models.SportCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String imagePath;

    private String altImgName;

    private String caption;

    private SportCategory category;

    private List<Comment> comments;

}
