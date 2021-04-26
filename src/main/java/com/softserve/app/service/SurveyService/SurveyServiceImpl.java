package com.softserve.app.service.SurveyService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.SurveyDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.Survey;
import com.softserve.app.models.User;
import com.softserve.app.repository.SurveyRepository;
import com.softserve.app.service.ConverterService.ConverterService;
import com.softserve.app.service.UserService.UserService;
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
                SportHubConstant.USER_NOT_FOUND.getMessage(), 404));
    }

    @Override
    public List<Survey> findAllByUser(User user) {
        return surveyRepository.findAllByUser(user);
    }

    @Override
    public SurveyDTO createSurvey(String surveyDTO, Long user_id) {
        SurveyDTO dto = converterService.convertStringToClass(surveyDTO, SurveyDTO.class);
        User user = userService.findById(user_id);

        Survey survey = new Survey();
        survey.setQuestion(dto.getQuestion());
        survey.setIsShown(dto.getIsShown() != null ? dto.getIsShown() : false);
        survey.setUser(user);
        return surveyRepository.save(survey).ofDTO();
    }

    @Override
    public void deleteSurvey(Long survey_id) {
        Survey survey = findById(survey_id);
        surveyRepository.delete(survey);
    }

}
