package com.softserve.app.controller;

import com.softserve.app.dto.CheckBoxDTO;
import com.softserve.app.models.CheckBox;
import com.softserve.app.models.Survey;
import com.softserve.app.models.User;
import com.softserve.app.service.CheckBoxService.CheckBoxService;
import com.softserve.app.service.SurveyService.SurveyService;
import com.softserve.app.service.UserService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CheckBoxController {
    private final CheckBoxService checkBoxService;
    private final UserService userService;
    private final SurveyService surveyService;

    @GetMapping("/user/{user_id}/surveys/{survey_id}")
    public List<CheckBox> findAllBySurveyId(
            @PathVariable Long user_id
            , @PathVariable Long survey_id) {

        User usr = userService.findById(user_id); // for control
        Survey srv = surveyService.findById(survey_id);

        return checkBoxService.findAllBySurvey(srv);
    }

    @PostMapping("/user/{user_id}/surveys/{survey_id}")
    public CheckBoxDTO createCheckBox(
            @PathVariable Long user_id
            , @PathVariable Long survey_id
            , @RequestParam(name = "checkBoxDTO") String checkBoxDTO) {
        return checkBoxService.createCheckBox(checkBoxDTO, survey_id);
    }

}
