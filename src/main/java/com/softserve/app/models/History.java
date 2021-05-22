package com.softserve.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softserve.app.dto.HistoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Article article;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    public HistoryDTO ofDTO() {
        return HistoryDTO.builder()
                .id(this.id)
                .article(this.article)
                .date(this.date)
                .user(this.user)
                .build();
    }
}