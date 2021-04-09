package com.softserve.app.models;

import com.softserve.app.dto.BannerDTO;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "image", nullable = false)
    private String imgPath;

    @Column(name = "lastUpdated", nullable = false)
    private String lastUpdated;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private SportCategory category;

    public enum Status {
        PUBLISHED, NOT_PUBLISHED, CLOSED;
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    public BannerDTO convertToDTO(){
        return BannerDTO.builder()
                .id(this.id)
                .title(this.title)
                .category(this.category)
                .imgPath(this.imgPath)
                .lastUpdated(this.lastUpdated)
                .status(this.status)
                .build();
    }

}
