package com.softserve.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softserve.app.dto.SurveyDTO;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@Component
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @NotNull
    @Column(name = "question")
    private String question;

    @NotNull
    @Column(name = "isOpen")
    private Boolean isOpen = true;

    public enum Status {
        Published, Unpublished;
    }
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status = Status.Unpublished;

    @Column(name = "closed_day")
    private LocalDate closed_day;

    public SurveyDTO ofDTO() {
        return SurveyDTO.builder()
                .id(this.id)
                .question(this.question)
                .isOpen(this.isOpen)
                .status(this.status)
                .closed_day(this.closed_day)
                .build();
    }
}
