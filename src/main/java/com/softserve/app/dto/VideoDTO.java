package com.softserve.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softserve.app.models.Comment;
import com.softserve.app.models.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
//@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoDTO implements Serializable {

    private Long id;

    private String url;

    private String title;

    List<Comment> comments = new ArrayList<>();

    private boolean isPublish;

    private boolean isUploaded;

    private boolean showComments;

    public Video ofEntity(){
        return Video.builder()
                .comments(this.comments)
                .id(this.id)
                .isPublish(this.isPublish)
                .isUploaded(this.isUploaded)
                .title(this.title)
                .url(this.url)
                .build();
    }
}
