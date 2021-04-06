package com.softserve.app.dto;

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
}
