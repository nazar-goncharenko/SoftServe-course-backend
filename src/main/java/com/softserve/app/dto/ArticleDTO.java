package com.softserve.app.dto;

import com.softserve.app.models.Comment;
import com.softserve.app.models.SportCategory;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
//@Builder
public class ArticleDTO {
    private String title;
    private String text;

//    чи потрібно тут вказувати n:n поля?
//    private Set<Comment> comments;
    private SportCategory category;
}
