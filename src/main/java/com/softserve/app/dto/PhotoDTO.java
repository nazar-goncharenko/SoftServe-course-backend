package com.softserve.app.dto;

import com.softserve.app.models.PhotoOfTheDay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO implements Serializable {
    private Long id;

    private String photoUrl;

    private String alt;

    private String photoTitle;

    private String description;

    private String author;

    private Boolean isShown;

    public PhotoOfTheDay fromDTO() {
        PhotoOfTheDay photo = new PhotoOfTheDay();
        photo.setId(this.id);
        photo.setPhotoUrl(this.photoUrl);
        photo.setAlt(this.alt);
        photo.setPhotoTitle(this.photoTitle);
        photo.setDescription(this.description);
        photo.setAuthor(this.author);
        photo.setIsShown(this.isShown);

        return photo;
    };
}
