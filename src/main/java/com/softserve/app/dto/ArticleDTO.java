package com.softserve.app.dto;

import com.softserve.app.models.SportCategory;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@Builder(toBuilder = true)
public class ArticleDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String imagePath;

    private SportCategory category;

}
