package com.softserve.app.dto;

import com.softserve.app.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CommentDTO {

    private Long id;

    private UserDTO author;

    private String comment;

    private Timestamp time;

    private List<EstimationDTO> estimations;

    public Comment ofEntity() {
        return Comment.builder()
                .author(this.author.ofEntity())
                .comment(this.comment)
                .estimations(this.estimations
                        .stream()
                        .map(EstimationDTO::ofEntity)
                        .collect(Collectors.toList()))
                .id(this.id)
                .time(this.time)
                .build();
    }
}
