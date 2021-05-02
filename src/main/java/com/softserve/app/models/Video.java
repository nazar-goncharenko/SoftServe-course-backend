package com.softserve.app.models;

import com.softserve.app.dto.VideoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder(toBuilder = true)
@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String title;

    @OneToMany
    List<Comment> comments = new ArrayList<>();

    private boolean uploaded;

    private boolean publish;

    private boolean showComments;

    public VideoDTO ofDTO(){
        return VideoDTO.builder()
                .id(this.id)
                .comments(this.comments)
                .uploaded(this.uploaded)
                .url(this.url)
                .title(this.title)
                .publish(this.publish)
                .showComments(this.showComments)
                .build();
    }

    public Video setFromDTO(VideoDTO videoDTO){
        this.title = videoDTO.getTitle();
        this.publish = videoDTO.isPublish();
        this.uploaded = videoDTO.isUploaded();
        this.url = videoDTO.getUrl();
        this.showComments = videoDTO.isShowComments();
        this.comments = videoDTO.getComments();
        return this;
    }
}
