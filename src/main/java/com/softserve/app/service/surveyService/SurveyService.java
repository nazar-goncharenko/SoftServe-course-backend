package com.softserve.app.service.surveyService;

import com.softserve.app.models.Survey;
import com.softserve.app.models.User;

import java.util.List;
import java.util.Optional;


public interface SurveyService {
    List<Survey> findAllByAuthor(User author);

    Optional<Survey> findById(Long id);
}
