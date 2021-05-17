package com.softserve.app.service.surveyService;

import com.softserve.app.dto.SurveyDTO;
import com.softserve.app.models.Survey;
import com.softserve.app.models.User;

import java.util.List;


public interface SurveyService {
    Survey findById(Long id);

    List<Survey> findAllByUser(User user);

    SurveyDTO createSurvey(SurveyDTO surveyDTO, Long user_id);

    SurveyDTO manageSurvey(Long survey_id);

    void deleteSurvey(Long survey_id);

    List<Survey> findAllFiltered(Long user_id, boolean isOpen);
}