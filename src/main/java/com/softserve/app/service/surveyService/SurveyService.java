package com.softserve.app.service.surveyService;

import com.softserve.app.dto.SurveyDTO;
import com.softserve.app.models.Survey;

import java.util.List;


public interface SurveyService {
    Survey findById(Long id);

    SurveyDTO createSurvey(SurveyDTO surveyDTO, Long user_id);

    SurveyDTO closeSurvey(Long survey_id);

    void deleteSurvey(Long survey_id);

    List<Survey> findAllFiltered(boolean isOpen);
    List<Survey> findAllFilteredAdmin(Long user_id, boolean isOpen);

    SurveyDTO changeStatusSurvey(Long survey_id);
}
