package com.softserve.app.controller;

import com.softserve.app.dto.VideoDTO;
import com.softserve.app.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/videos")
@CrossOrigin
public class VideoController {

    private final VideoService videoService;


    @GetMapping
    List<VideoDTO> getAll(){
        return videoService.getAll();
    }


    @GetMapping("/{id}")
    VideoDTO getById(@PathVariable Long id){
        return videoService.getById(id);
    }

    @PostMapping
    void save(@RequestBody(required = false) VideoDTO videoDTO,
              @RequestParam(name = "file", required = false) MultipartFile file){
        System.out.println(file);
//        videoService.save(videoDTO, file);
    }
}
