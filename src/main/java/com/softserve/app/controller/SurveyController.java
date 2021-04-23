package com.softserve.app.controller;

import com.softserve.app.dto.SurveyDTO;
import com.softserve.app.models.Survey;
import com.softserve.app.models.User;
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
    public SurveyDTO createSurvey(
            @PathVariable Long user_id
            , @RequestParam(name = "surveyDTO") String surveyDTO) {
        return surveyService.createSurvey(surveyDTO, user_id);
    }

}
