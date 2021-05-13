package com.softserve.app.controller;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.CheckBoxDTO;
import com.softserve.app.models.Survey;
import com.softserve.app.service.checkBoxService.CheckBoxService;
import com.softserve.app.service.surveyService.SurveyService;
import com.softserve.app.service.userService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public List<CheckBoxDTO> findAllBySurvey(
            @PathVariable Long user_id
            , @PathVariable Long survey_id) {
        Survey srv = surveyService.findById(survey_id);
        return checkBoxService.findAllBySurvey(srv);
    }

    @PostMapping("/user/{user_id}/surveys/{survey_id}/checkbox")
    public ResponseEntity<CheckBoxDTO> createCheckBox(
            @PathVariable Long user_id
            , @PathVariable Long survey_id
            , @RequestParam(name = "checkBoxDTO") String checkBoxDTO) {
        return ResponseEntity.ok(checkBoxService.createCheckBox(checkBoxDTO, survey_id));
    }

    @PutMapping("/user/{user_id}/surveys/{survey_id}/checkbox")
    public ResponseEntity<String> createManyCheckBoxes(
            @PathVariable Long user_id
            , @PathVariable Long survey_id
            , @RequestParam(name = "checkBoxManyDTO") String checkBoxManyDTO) {
        checkBoxService.createManyCheckBoxes(checkBoxManyDTO, survey_id);
        return ResponseEntity.ok(SportHubConstant.CheckBox_LIST_CREATED.getMessage());
    }

    @DeleteMapping("/user/{user_id}/surveys/{survey_id}/{checkBox_id}")
    public ResponseEntity<String> deleteCheckBox(
            @PathVariable Long user_id
            , @PathVariable Long survey_id
            , @PathVariable Long checkBox_id) {
        checkBoxService.deleteCheckBox(checkBox_id);
        return ResponseEntity.ok(SportHubConstant.CheckBox_DELETED.getMessage());
    }
}
