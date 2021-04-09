package com.softserve.app.controller;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.UserDTO;
import com.softserve.app.models.User;
import com.softserve.app.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/user")
public class UserController {

    // todo: user registration and login

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getUsers() {
        List<User> usList = userService.findAll();
        return new ResponseEntity<>(usList, HttpStatus.OK);
    }


    // show  profile
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> showProfile(
            @RequestBody UserDTO userDto) {

        User usr = userService.findByEmail(userDto.getEmail());

        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(usr);
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(SportHubConstant.AUTHENTICATION_EXCEPTION.getMessage());
    }


    // update profile
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity<Object> updatePersonal(
            @RequestBody UserDTO userDto) {

        User usr = userService.findById(userDto.getId());

        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(userService.updateUser(userDto));
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(SportHubConstant.AUTHENTICATION_EXCEPTION.getMessage());
    }


    // update avatar
    @RequestMapping(value = "/profile/avatar", method = RequestMethod.POST)
    public ResponseEntity<Object> updateAvatar(
            @RequestParam("user_id") Long user_id,
            @RequestParam("file") MultipartFile userAva
    ) {
        User usr = userService.findById(user_id);

        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(userService.updateAvatar(user_id, userAva));
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(SportHubConstant.AUTHENTICATION_EXCEPTION.getMessage());
    }
}
