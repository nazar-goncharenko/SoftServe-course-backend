package com.softserve.app.service.SurveyService;

import com.softserve.app.models.Survey;

import java.util.List;


public interface SurveyService {
    List<Survey> findAllByAuthor();

    Survey findById(Long id);
}
