package com.softserve.app.controller;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.SurveyDTO;
import com.softserve.app.models.Survey;
import com.softserve.app.models.User;
import com.softserve.app.service.SurveyService.SurveyService;
import com.softserve.app.service.UserService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class SurveyController {

    private final SurveyService surveyService;
    private final UserService userService;

    @GetMapping("/user/{user_id}/surveys")
    public List<Survey> findAllByUserId(
            @PathVariable Long user_id) {
        User usr = userService.findById(user_id);
        return surveyService.findAllByUser(usr);
    }

    @PostMapping("/user/{user_id}/surveys")
    public ResponseEntity<SurveyDTO> createSurvey(
            @PathVariable Long user_id
            , @RequestParam(name = "surveyDTO") String surveyDTO) {
        return ResponseEntity.ok(surveyService.createSurvey(surveyDTO, user_id));
    }

    @PostMapping("/user/{user_id}/surveys/{survey_id}")
    public ResponseEntity<SurveyDTO> updateSurvey(
            @PathVariable Long user_id
            , @PathVariable Long survey_id
            , @RequestParam(name = "surveyDTO") String surveyDTO) {
        return ResponseEntity.ok(surveyService.updateSurvey(surveyDTO));
    }

    @DeleteMapping("/user/{user_id}/surveys/{survey_id}")
    public ResponseEntity<String> deleteSurvey(
            @PathVariable Long user_id
            , @PathVariable Long survey_id) {
        surveyService.deleteSurvey(survey_id);
        return ResponseEntity.ok(SportHubConstant.SURVEY_DELETED.getMessage());
    }

    @GetMapping("/user/{user_id}/surveys/open")
    public List<Survey> findAllOpen(
            @PathVariable Long user_id) {
        List<Survey> openSurveys = new ArrayList<>();
        User usr = userService.findById(user_id);
        List<Survey> surveys =  surveyService.findAllByUser(usr);
        for (Survey s : surveys){
            if (s.getIsOpen()) {
                openSurveys.add(s);
            }
        }
        return openSurveys;
    }

    @GetMapping("/user/{user_id}/surveys/closed")
    public List<Survey> findAllClosed(
            @PathVariable Long user_id) {
        List<Survey> closedSurveys = new ArrayList<>();
        User usr = userService.findById(user_id);
        List<Survey> surveys =  surveyService.findAllByUser(usr);
        for (Survey s : surveys){
            if (!s.getIsOpen()) {
                closedSurveys.add(s);
            }
        }
        return closedSurveys;
    }

}
