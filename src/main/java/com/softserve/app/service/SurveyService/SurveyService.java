package com.softserve.app.service.SurveyService;

import com.softserve.app.dto.SurveyDTO;
import com.softserve.app.models.Survey;
import com.softserve.app.models.User;

import java.util.List;


public interface SurveyService {
    Survey findById(Long id);

    List<Survey> findAllByUser(User user);

    SurveyDTO createSurvey(String surveyDTO, Long user_id);
}
