package com.softserve.app.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.softserve.app.models.User;
import com.softserve.app.models.View;
import com.softserve.app.service.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {


    @Autowired
    private UserServiceImpl userService;


    @PostMapping("/registration")
    @JsonView(View.UserInfo.class)
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);

    }
}

