package com.softserve.app.service.SurveyService;

import com.softserve.app.models.Survey;
import com.softserve.app.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyServiceImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public List<Survey> findAllByAuthor() {
        return null;
    }

    @Override
    public Survey findById(Long id) {
        return null;
    }


}
