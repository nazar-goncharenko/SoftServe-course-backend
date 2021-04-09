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

import java.util.Objects;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user/{user_id}")
public class UserController {

    // todo: user registration and login

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // show  profile
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> showProfile(
            @PathVariable Long user_id) {

        User usr = userService.findById(user_id);

        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(usr);
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(SportHubConstant.AUTHENTICATION_EXCEPTION.getMessage());
    }


    // update profile
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> updatePersonal(
            @RequestBody UserDTO userDto, @PathVariable Long user_id) {

        User usr = userService.findById(user_id);

        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(userService.updateUser(userDto));
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(SportHubConstant.AUTHENTICATION_EXCEPTION.getMessage());
    }


    // update avatar
    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    public ResponseEntity<Object> updateAvatar(
            @RequestParam("file") MultipartFile userAva,
            @PathVariable Long user_id) {
        User usr = userService.findById(user_id);

        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(userService.updateAvatar(user_id, userAva));
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(SportHubConstant.AUTHENTICATION_EXCEPTION.getMessage());
    }
}
