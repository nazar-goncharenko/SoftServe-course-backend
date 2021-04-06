package com.softserve.app.controller;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.User;
import com.softserve.app.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


@Controller
public class UserController {

    @Autowired
    private UserService userService;


    // todo: user registration and login


    // show  profile
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity showProfile(
            @RequestBody Long id // id of user that u want to see
    ) {
        User usr = userService.findById(id);
        if (Objects.equals(userService.getCurrentUser().getId(), usr.getId())) {
            return ResponseEntity.ok(usr);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    // update profile  'Personal' tab // todo: update  avatar
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity updatePersonal(
            @RequestBody User user // user that u want to update
    ) {
        if (Objects.equals(userService.getCurrentUser().getId(), user.getId())) {
            return ResponseEntity.ok(userService.updateUser(user));
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    // todo: update PASSWORD

    // show Surveys
    @RequestMapping(value = "/profile/surveys", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity showSurveys(
            @RequestBody User user // user that u want to update
    ) {
        if (Objects.equals(userService.getCurrentUser().getId(), user.getId())) {
            return ResponseEntity.ok(// return list of surveys); // todo: list of surveys !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    // update Survey
    @RequestMapping(value = "/profile/surveys", method = RequestMethod.POST)
    public ResponseEntity updateSurvey(
            @RequestParam("id") Long id, // user id
            @RequestParam("surveyId") Long surveyId
    ) {
        User usr = userService.findById(id);
        if (Objects.equals(userService.getCurrentUser().getId(), usr.getId())) {
            return ResponseEntity.ok(// return list of surveys); // todo: update surveys -> SurveyRepo and SurveyService!!!!!!!!!!!!!!!!!!
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    // show Favourites
    @RequestMapping(value = "/profile/favourites", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity showFavourites(
            @RequestBody User user // user that u want to update
    ) {
        if (Objects.equals(userService.getCurrentUser().getId(), user.getId())) {
            return ResponseEntity.ok(// return list of favourites); // todo: update favourites -> list of favourites !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    //Favourite update - on Favourite article

}
