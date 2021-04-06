package com.softserve.app.controllers;

import com.softserve.app.dto.PhotoDTO;
import com.softserve.app.models.PhotoOfTheDay;
import com.softserve.app.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/photo")
    public PhotoOfTheDay updatePhoto(@RequestPart("photo_info")PhotoDTO photo, @RequestPart("img")MultipartFile image) throws IOException {

        return photoService.savePhoto(photo, image);
    }
}
