package com.softserve.app.controller;

import com.softserve.app.dto.PhotoDTO;
import com.softserve.app.service.PhotoService.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final PhotoService photoService;

    @PostMapping
    public PhotoDTO addPhoto(@RequestParam("photoDTO") String photoDTO, @RequestParam("img") MultipartFile image) {

        return photoService.savePhoto(photoDTO, image);
    }

}
