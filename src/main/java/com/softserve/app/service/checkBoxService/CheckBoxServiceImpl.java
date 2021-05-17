package com.softserve.app.service.checkBoxService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.CheckBoxDTO;
import com.softserve.app.dto.CheckBoxManyDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.CheckBox;
import com.softserve.app.models.Survey;
import com.softserve.app.repository.CheckBoxRepository;
import com.softserve.app.service.converterService.ConverterService;
import com.softserve.app.service.surveyService.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckBoxServiceImpl implements CheckBoxService {
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
        return checkBoxRepository.findAllBySurvey(survey).stream()
                .map(CheckBox::ofDTO).collect(Collectors.toList());
    }

    @Override
    public CheckBoxDTO createCheckBox(CheckBoxDTO dto, Long survey_id) {
        Survey survey = surveyService.findById(survey_id);

        CheckBox checkBox = new CheckBox();
        checkBox.setText(dto.getText());
        checkBox.setResponses_count(dto.getResponses_count() != null ? dto.getResponses_count() : 0L);
        checkBox.setSurvey(survey);
        return checkBoxRepository.save(checkBox).ofDTO();
    }

    @Override
    public void createManyCheckBoxes(String checkBoxManyDTO, Long survey_id) {
        CheckBoxManyDTO manyDtos = converterService.convertStringToClass(checkBoxManyDTO, CheckBoxManyDTO.class);
        Survey survey = surveyService.findById(survey_id);

        manyDtos.getListOfCheckBoxes().forEach
                (eachDto -> {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setText(eachDto.getText());
                    checkBox.setResponses_count(eachDto.getResponses_count() != null ? eachDto.getResponses_count() : 0L);
                    checkBox.setSurvey(survey);
                    checkBoxRepository.save(checkBox);
                });
    }

    @Override
    public void deleteCheckBox(Long checkBox_id) {
        checkBoxRepository.deleteById(checkBox_id);
    }
}
