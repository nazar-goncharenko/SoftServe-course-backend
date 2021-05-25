package com.softserve.app.service.surveyService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.SurveyDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.Survey;
import com.softserve.app.models.User;
import com.softserve.app.repository.SurveyRepository;
import com.softserve.app.service.converterService.ConverterService;
import com.softserve.app.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepository surveyRepository;
    private final UserService userService;
    private final ConverterService converterService;


    @Override
    public Survey findById(Long id) {
        return surveyRepository.findById(id).orElseThrow(() -> new SportHubException(
                SportHubConstant.SURVEY_DELETED.getMessage(), 404));
    }

    @Override
    public List<Survey> findAllByUser(User user) {
        return surveyRepository.findAllByUser(user);
    }

    @Override
    public List<Survey> findAllFiltered(Long user_id, boolean isOpen) {
        User usr = userService.findById(user_id);
        return surveyRepository.findAllByUserAndIsOpen(usr, isOpen);
    }

    @Override
    public SurveyDTO createSurvey(SurveyDTO dto, Long user_id) {
        User user = userService.findById(user_id);

        Survey survey = new Survey();
        survey.setQuestion(dto.getQuestion());
        survey.setIsOpen(dto.getIsOpen() != null ? dto.getIsOpen() : false);
        survey.setUser(user);
        return surveyRepository.save(survey).ofDTO();
    }

    @Override
    public SurveyDTO manageSurvey(Long survey_id) {
        Survey srvFromDb = findById(survey_id);
        srvFromDb.setIsOpen(!srvFromDb.getIsOpen());

        return surveyRepository.save(srvFromDb).ofDTO();
    }

    @Override
    public void deleteSurvey(Long survey_id) {
        surveyRepository.deleteById(survey_id);
    }

}
