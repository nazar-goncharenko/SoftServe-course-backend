package com.softserve.app.models;

import com.softserve.app.dto.PhotoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class PhotoOfTheDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "alt")
    private String alt;

    @Column(name = "photo_title")
    private String photoTitle;

    @Column(name = "description")
    private String description;

    @Column(name = "author")
    private String author;

    @Column(name = "is_shown")
    private Boolean isShown;

    public PhotoDTO toDTO() {
        return PhotoDTO.builder()
                .id(this.id)
                .photoUrl(this.photoUrl)
                .alt(this.alt)
                .photoTitle(this.photoTitle)
                .description(this.description)
                .author(this.author)
                .isShown(this.isShown)
                .build();
    }

}