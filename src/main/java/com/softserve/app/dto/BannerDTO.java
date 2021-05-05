package com.softserve.app.dto;

import com.softserve.app.models.Banner;
import com.softserve.app.models.SportCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BannerDTO implements Serializable {

    private Long id;

    private String title;

    private String imgPath;

    private String lastUpdated;

    private SportCategory category;

    private Banner.Status status;

}