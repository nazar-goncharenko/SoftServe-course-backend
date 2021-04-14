package com.softserve.app.controller;

import com.softserve.app.dto.PhotoDTO;
import com.softserve.app.models.PhotoOfTheDay;
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
    public Long addPhotoDTO(@RequestBody PhotoDTO photoDTO) {

        PhotoOfTheDay photo = photoService.savePhotoDTO(photoDTO);
        return photo.getId();
    }

    @PutMapping
    public PhotoDTO addPhoto(@RequestParam("id") Long id, @RequestParam("img") MultipartFile image) {

        return photoService.savePhoto(id, image);
    }

}
