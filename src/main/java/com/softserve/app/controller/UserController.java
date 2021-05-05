package com.softserve.app.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.UserDTO;
import com.softserve.app.models.User;
import com.softserve.app.models.View;
import com.softserve.app.repository.UserRepository;
import com.softserve.app.models.ResetPasswordRequest;
import com.softserve.app.service.ResetService;
import com.softserve.app.service.userService.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpPost;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    private final ResetService resetService;

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user).ofDTO(), HttpStatus.OK);
    }

    @GetMapping("/users")
    List<User> getAllUser(@RequestHeader HttpHeaders headers) {
        System.out.println(headers);
        return userRepository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginAuth(@RequestBody UserDTO userDTO) {
        userService.authorize(userDTO);
        return new ResponseEntity(userService.authorize(userDTO), HttpStatus.OK);
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

    // todo: user registration and login


    // show  profile
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> showProfile(
            @RequestBody UserDTO userDto
    ) {

        UserDTO usr = userService.findByEmail(userDto.getEmail());
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
        UserDTO usr = userService.findByEmail(userDto.getEmail());
        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(userService.updateUser(usr));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
