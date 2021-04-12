package com.softserve.app.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.UserDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.ResetPasswordRequest;
import com.softserve.app.models.User;
import com.softserve.app.models.View;
import com.softserve.app.service.ResetService;
import com.softserve.app.service.UserService.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;


@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/user/{user_id}")
    public ResponseEntity<UserDTO> showProfile(
            @PathVariable Long user_id) {

        User usr = userService.findById(user_id);
        if (Objects.equals(userService.getCurrentUser(), usr)) {
            UserDTO userDTO = usr.ofDTO();
            return ResponseEntity.ok(userDTO);
        }
        throw new SportHubException(SportHubConstant.AUTHORIZE_EXCEPTION.getMessage(), 403);
    }


    @PostMapping("/user/{user_id}")
    public ResponseEntity<UserDTO> updatePersonal(
            @RequestBody UserDTO userDto, @PathVariable Long user_id) {

        User usr = userService.findById(user_id);

        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(userService.updateUser(userDto));
        }
        throw new SportHubException(SportHubConstant.AUTHORIZE_EXCEPTION.getMessage(), 403);
    }


    @PostMapping("/user/{user_id}/avatar")
    public ResponseEntity<UserDTO> updateAvatar(
            @RequestParam("file") MultipartFile userAva,
            @PathVariable Long user_id) {
        User usr = userService.findById(user_id);

        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(userService.updateAvatar(user_id, userAva));
        }
        throw new SportHubException(SportHubConstant.AUTHORIZE_EXCEPTION.getMessage(), 403);
    }


    // Registration


    private final ResetService resetService;
    @PostMapping("/registration")
    @JsonView(View.UserInfo.class)
    public ResponseEntity<UserDTO> addUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user.ofDTO(), HttpStatus.OK);
    }

    // just for test
    @GetMapping("/users")
    List<User> getAllUser() {
        return userService.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginAuth(@RequestBody UserDTO userDTO) {
        userService.authorize(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @ApiOperation(value = "Get email for reset",
            notes = "Send message to your email\"")
    @PostMapping("/forgot_password")
    public ResponseEntity<UserDTO> processForgotPassword(
            @ApiParam(
                    name = "email",
                    type = "String",
                    value = "User Email",
                    example = "example@gmail.com",
                    required = true) @RequestBody UserDTO userDTO) {
        resetService.createToken(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Reset password",
            notes = "Input passwords\"")
    @PostMapping("/reset_password")
    public ResponseEntity<UserDTO> processResetPassword(
            @RequestBody ResetPasswordRequest resetPasswordRequest) {
        resetService.resetPassword(resetPasswordRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
