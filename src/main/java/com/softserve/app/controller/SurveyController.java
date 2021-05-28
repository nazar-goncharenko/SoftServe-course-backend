package com.softserve.app.controller;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.SurveyDTO;
import com.softserve.app.models.Survey;
import com.softserve.app.service.surveyService.SurveyService;
import com.softserve.app.service.userService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/admin/{user_id}/surveys")
    public ResponseEntity<SurveyDTO> createSurvey(
            @PathVariable Long user_id
            , @RequestBody SurveyDTO surveyDTO) {
        return ResponseEntity.ok(surveyService.createSurvey(surveyDTO, user_id));
    }

    @PostMapping("/admin/{user_id}/surveys/{survey_id}")
    public ResponseEntity<SurveyDTO> closeSurvey(
            @PathVariable Long user_id
            , @PathVariable Long survey_id) {
        return ResponseEntity.ok(surveyService.closeSurvey(survey_id));
    }

    @PostMapping("/admin/{user_id}/surveys/{survey_id}/changeStatusSurvey")
    public ResponseEntity<SurveyDTO> changeStatusSurvey(
            @PathVariable Long user_id
            , @PathVariable Long survey_id) {
        return ResponseEntity.ok(surveyService.changeStatusSurvey(survey_id));
    }

    @DeleteMapping("/admin/{user_id}/surveys/{survey_id}")
    public ResponseEntity<String> deleteSurvey(
            @PathVariable Long user_id
            , @PathVariable Long survey_id) {
        surveyService.deleteSurvey(survey_id);
        return ResponseEntity.ok(SportHubConstant.SURVEY_DELETED.getMessage());
    }

    @GetMapping("/surveys/open")
    public ResponseEntity<List<Survey>> findAllOpen() {
        return ResponseEntity.ok(surveyService.findAllFiltered(true));
    }

    @GetMapping("/admin/{user_id}/surveys/open")
    public ResponseEntity<List<Survey>> findAllOpenAdmin(
            @PathVariable Long user_id) {
        return ResponseEntity.ok(surveyService.findAllFilteredAdmin(user_id, true));
    }

    @GetMapping("/surveys/closed")
    public ResponseEntity<List<Survey>> findAllClosed() {
        return ResponseEntity.ok(surveyService.findAllFiltered(false));
    }

    @GetMapping("/admin/{user_id}/surveys/closed")
    public ResponseEntity<List<Survey>> findAllClosedAdmin(
            @PathVariable Long user_id) {
        return ResponseEntity.ok(surveyService.findAllFilteredAdmin(user_id, false));
    }

}
