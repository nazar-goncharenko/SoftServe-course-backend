package com.softserve.app.dto;

import com.softserve.app.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VideoDTO {

    private Long id;

    private String url;

    private String title;

    List<Comment> comments = new ArrayList<>();

    private boolean isPublish;

    private boolean isUploaded;
}
