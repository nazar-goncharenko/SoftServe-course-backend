package com.softserve.app.dto;

import com.softserve.app.models.Article;
import com.softserve.app.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class HistoryDTO {
    private Long id;
    private Article article;
    private LocalDateTime date;
    private User user;
}
