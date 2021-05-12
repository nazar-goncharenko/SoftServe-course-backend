package com.softserve.app.controller;

import com.softserve.app.dto.UserDTO;
import com.softserve.app.models.ResetPasswordRequest;
import com.softserve.app.models.User;
import com.softserve.app.service.resetService.ResetService;
import com.softserve.app.service.userService.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;


    @GetMapping("/user/{user_id}")
    public ResponseEntity<UserDTO> getUser(
            @PathVariable Long user_id) {

        User usr = userService.findById(user_id);
        return ResponseEntity.ok(usr.ofDTO());
    }


    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<HttpStatus> deleteUser(
            @PathVariable Long user_id) {
        userService.deleteUser(user_id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


    @PostMapping("/user/{user_id}")
    public ResponseEntity<UserDTO> updateUser(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam(name = "userDTO") String userDTO) {

        return ResponseEntity.ok(userService.updateUser(file, userDTO));
    }


    // Registration


    private final ResetService resetService;

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user).ofDTO(), HttpStatus.OK);
    }



    @PostMapping(value = "/login")
    public ResponseEntity<UserDTO> loginAuth(@RequestBody UserDTO userDTO) {
        userService.authorize(userDTO);
        return new ResponseEntity(userService.getByDTO(userDTO), HttpStatus.OK);
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

    @GetMapping("user/email/{email}")
    public UserDTO getByEmail(@PathVariable String email){
        return userService.findByEmail(email).ofDTO();
    }
}
