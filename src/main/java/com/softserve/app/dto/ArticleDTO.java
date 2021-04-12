package com.softserve.app.dto;

import com.softserve.app.models.SportCategory;
import lombok.Builder;
import lombok.Data;


@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
//@Builder
public class ArticleDTO {
    private Long id;
    private String title;
    private String description;
    private SportCategory category;
}
