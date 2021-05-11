package com.softserve.app.service.CheckBoxService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.CheckBoxDTO;
import com.softserve.app.dto.CheckBoxManyDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.CheckBox;
import com.softserve.app.models.Survey;
import com.softserve.app.repository.CheckBoxRepository;
import com.softserve.app.service.ConverterService.ConverterService;
import com.softserve.app.service.SurveyService.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<CheckBoxDTO> findAllBySurvey(Survey survey) {
        List<CheckBox> checkBoxes = checkBoxRepository.findAllBySurvey(survey);

        List<CheckBoxDTO> cbDto = new ArrayList<>();
        for (CheckBox cb : checkBoxes) {
            cbDto.add(cb.ofDTO());
        }
        return cbDto;
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

    @Override
    public void createManyCheckBoxes(String checkBoxManyDTO, Long survey_id) {
        CheckBoxManyDTO manyDtos = converterService.convertStringToClass(checkBoxManyDTO, CheckBoxManyDTO.class);
        Survey survey = surveyService.findById(survey_id);

        for (CheckBoxDTO eachDto : manyDtos.getListOfCheckBoxes()) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(eachDto.getText());
            checkBox.setResponses_count(eachDto.getResponses_count() != null ? eachDto.getResponses_count() : 0L);
            checkBox.setSurvey(survey);
            checkBoxRepository.save(checkBox);
        }
    }

    @Override
    public void deleteCheckBox(Long checkBox_id) {
        CheckBox checkBox = findById(checkBox_id);
        checkBoxRepository.delete(checkBox);
    }
}
