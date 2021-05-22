package com.softserve.app.models;

import com.softserve.app.dto.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private User author;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Estimation> estimations;

    public CommentDTO ofDTO() {
        return CommentDTO.builder()
                .author(this.author.ofDTO())
                .comment(this.comment)
                .estimations(this.estimations
                        .stream()
                        .map(Estimation::ofDTO)
                        .collect(Collectors.toList()))
                .id(this.id)
                .time(this.time)
                .build();
    }

    public void addEstimation(Estimation estimation){
        this.estimations.add(estimation);
    }

    public void deleteEstimation(Estimation estimation){
        this.estimations.remove(estimation);
    }
}
