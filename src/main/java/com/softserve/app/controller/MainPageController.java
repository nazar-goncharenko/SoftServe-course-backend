package com.softserve.app.controller;

import com.softserve.app.dto.PhotoDTO;
import com.softserve.app.models.User;
import com.softserve.app.service.photoService.PhotoService;
import com.softserve.app.service.userService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class MainPageController {

    private final UserService userService;
    private final PhotoService photoService;

    @GetMapping
    public PhotoDTO showPhoto() {
        return photoService.showPhoto();
    }

    // just for test
    @GetMapping("/users")
    ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(userService.findAll());
    }

}
