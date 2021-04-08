package com.softserve.app.controller;

import com.softserve.app.dto.UserDTO;
import com.softserve.app.models.User;
import com.softserve.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController
@AllArgsConstructor
public class UserController {


    // todo: user registration and login


    private final UserService userService;

    // todo: add methods

}
