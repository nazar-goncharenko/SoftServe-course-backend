package com.softserve.app.controller;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.UserDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.Survey;
import com.softserve.app.models.User;
import com.softserve.app.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;


@Controller
public class UserController {


    // todo: user registration and login


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // show  profile
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> showProfile(
            @RequestBody UserDTO userDto
    ) {
        User usr = userService.findByEmail(userDto.getEmail());
        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(usr);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // update profile  'Personal' tab // todo: update  avatar
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity<Object> updatePersonal(
            @RequestBody UserDTO userDto
    ) {
        User usr = userService.findByEmail(userDto.getEmail());
        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(userService.updateUser(usr));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // todo: update PASSWORD
/*
    // show Surveys
    @RequestMapping(value = "/profile/surveys", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Survey>> showSurveys(
            @RequestBody UserDTO userDto
    ) {
        User usr = userService.findByEmail(userDto.getEmail());
        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(// return list of surveys); // todo: list of surveys !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // update Survey
    @RequestMapping(value = "/profile/surveys", method = RequestMethod.POST)
    public ResponseEntity<List<Survey>> updateSurvey(
            @RequestParam("id") Long id, // user id
            @RequestParam("surveyId") Long surveyId
    ) {
        User usr = userService.findById(id);
        if (Objects.equals(userService.getCurrentUser().getId(), usr.getId())) {
            return ResponseEntity.ok(// return list of surveys); // todo: update surveys -> SurveyRepo and SurveyService!!!!!!!!!!!!!!!!!!
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // show Favourites
    @RequestMapping(value = "/profile/favourites", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Object>> showFavourites(
            @RequestBody UserDTO userDto
    ) {
        User usr = userService.findByEmail(userDto.getEmail());
        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(// return list of favourites); // todo: update favourites -> list of favourites !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //Favourite update - on Favourite article
*/

}
