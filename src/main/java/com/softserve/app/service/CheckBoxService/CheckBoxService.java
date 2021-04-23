package com.softserve.app.service.CheckBoxService;

import com.softserve.app.dto.CheckBoxDTO;
import com.softserve.app.models.CheckBox;
import com.softserve.app.models.Survey;

import java.util.List;


public interface CheckBoxService {
    CheckBox findById(Long id);

    List<CheckBox> findAllBySurvey(Survey survey);

    CheckBoxDTO createCheckBox(String checkBoxDTO, Long survey_id);
}
