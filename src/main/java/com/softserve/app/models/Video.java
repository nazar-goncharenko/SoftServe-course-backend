package com.softserve.app.models;

import com.softserve.app.dto.VideoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String title;

    @OneToMany
    List<Comment> comments = new ArrayList<>();

    private boolean isUploaded;

    private boolean isPublish;

    public VideoDTO ofDTO(){
        return VideoDTO.builder()
                .id(this.id)
                .comments(this.comments)
                .isUploaded(this.isUploaded)
                .url(this.url)
                .title(this.title)
                .isPublish(this.isPublish)
                .build();
    }
}
