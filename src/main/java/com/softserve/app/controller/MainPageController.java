package com.softserve.app.controller;

import com.softserve.app.models.User;
import com.softserve.app.service.UserService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MainPageController {

    private final UserService userService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> showMainPage() {
        return  ResponseEntity.ok("You are at main page ^^");
    }


    // just for testing
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getUsers() {
        List<User> usList = userService.findAll();
        return new ResponseEntity<>(usList, HttpStatus.OK);
    }
}
