package com.softserve.app.service.CheckBoxService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.CheckBoxDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.CheckBox;
import com.softserve.app.models.Survey;
import com.softserve.app.repository.CheckBoxRepository;
import com.softserve.app.service.ConverterService.ConverterService;
import com.softserve.app.service.SurveyService.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckBoxServiceImpl implements CheckBoxService{
    private final CheckBoxRepository checkBoxRepository;
    private final ConverterService converterService;
    private final SurveyService surveyService;

    @Override
    public CheckBox findById(Long id) {
        return checkBoxRepository.findById(id).orElseThrow(() -> new SportHubException(
                SportHubConstant.USER_NOT_FOUND.getMessage(), 404));
    }

    @Override
    public List<CheckBox> findAllBySurvey(Survey survey) {
        return checkBoxRepository.findAllBySurvey(survey);
    }

    @Override
    public CheckBoxDTO createCheckBox(String checkBoxDTO, Long survey_id) {
        CheckBoxDTO dto = converterService.convertStringToClass(checkBoxDTO, CheckBoxDTO.class);
        Survey survey = surveyService.findById(survey_id);

        CheckBox checkBox = new CheckBox();
        checkBox.setText(dto.getText());
        checkBox.setResponses_count((long) 0);
        checkBox.setSurvey(survey);
        return checkBoxRepository.save(checkBox).ofDTO();
    }
}
