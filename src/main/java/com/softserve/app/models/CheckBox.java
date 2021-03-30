package com.softserve.app.models;

import javax.persistence.*;

@Entity
public class CheckBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Survey survey;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "responses_count", nullable = false)
    private Long responses_count;

    public CheckBox() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getResponses_count() {
        return responses_count;
    }

    public void setResponses_count(Long responses_count) {
        this.responses_count = responses_count;
    }
}
