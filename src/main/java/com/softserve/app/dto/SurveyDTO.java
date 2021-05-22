package com.softserve.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softserve.app.models.Survey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyDTO {
    private Long id;
    private String question;
    private Boolean isOpen;
    private Survey.Status status;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate closed_day;
    private Long responses_count;
}
