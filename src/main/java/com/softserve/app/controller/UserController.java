package com.softserve.app.controller;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.UserDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.User;
import com.softserve.app.service.UserService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RequestMapping("/user/{user_id}")
public class UserController {

    // todo: user registration and login

    private final UserService userService;


    @GetMapping
    public UserDTO showProfile(
            @PathVariable Long user_id) {

        User usr = userService.findById(user_id);
        if (Objects.equals(userService.getCurrentUser(), usr)) {
            UserDTO userDTO = usr.ofDTO();
            return userDTO;
        }
        throw new SportHubException(SportHubConstant.AUTHORIZE_EXCEPTION.getMessage(), 403);
    }


    @PostMapping
    public UserDTO updatePersonal(
            @RequestBody UserDTO userDto, @PathVariable Long user_id) {

        User usr = userService.findById(user_id);

        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return userService.updateUser(userDto);
        }
        throw new SportHubException(SportHubConstant.AUTHORIZE_EXCEPTION.getMessage(), 403);
    }


    @PostMapping("/avatar")
    public UserDTO updateAvatar(
            @RequestParam("file") MultipartFile userAva,
            @PathVariable Long user_id) {
        User usr = userService.findById(user_id);

        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return userService.updateAvatar(user_id, userAva);
        }
        throw new SportHubException(SportHubConstant.AUTHORIZE_EXCEPTION.getMessage(), 403);
    }
}
