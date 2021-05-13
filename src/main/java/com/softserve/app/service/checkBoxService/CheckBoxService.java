package com.softserve.app.service.checkBoxService;

import com.softserve.app.dto.CheckBoxDTO;
import com.softserve.app.models.CheckBox;
import com.softserve.app.models.Survey;

import java.util.List;


public interface CheckBoxService {
    CheckBox findById(Long id);

    List<CheckBoxDTO> findAllBySurvey(Survey survey);

    CheckBoxDTO createCheckBox(String checkBoxDTO, Long survey_id);

    void createManyCheckBoxes(String checkBoxManyDTO, Long survey_id);

    void deleteCheckBox(Long checkBox_id);
}
