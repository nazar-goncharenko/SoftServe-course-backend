package com.softserve.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softserve.app.models.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoDTO implements Serializable {

    private Long id;

    private String url;

    private String title;

    List<CommentDTO> comments = new ArrayList<>();

    private boolean publish;

    private boolean uploaded;

    private boolean showComments;

    public Video ofEntity() {
        return Video.builder()
                .comments(this.comments
                        .stream()
                        .map(CommentDTO::ofEntity)
                        .collect(Collectors.toList()))
                .id(this.id)
                .publish(this.publish)
                .uploaded(this.uploaded)
                .title(this.title)
                .url(this.url)
                .showComments(this.showComments)
                .build();
    }
}
