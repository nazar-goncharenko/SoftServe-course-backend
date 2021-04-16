package com.softserve.app.controller;

import com.softserve.app.dto.VideoDTO;
import com.softserve.app.service.VideoService.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/videos")
@CrossOrigin(origins = "*")
public class VideoController {

    private final VideoService videoService;


    @GetMapping
    public List<VideoDTO> getAll() {
        return videoService.getAll();
    }


    @GetMapping("/{id}")
    public VideoDTO getById(@PathVariable Long id) {
        return videoService.getById(id);
    }

    @PostMapping
    public VideoDTO saveFile(@RequestParam(name = "file") MultipartFile file,
                             @RequestParam(name = "videoDTO") String videoDTO) {
        return videoService.save(file, videoDTO);
    }

//    @PutMapping("/{id}")
//    public VideoDTO changeDTO(@PathVariable Long id, @RequestBody VideoDTO videoDTO) {
//        System.out.println(id);
//        return null;
//    }
//
//    @PutMapping("/{id}/video")
//    public VideoDTO changeVideo(@PathVariable Long id, @RequestBody VideoDTO videoDTO) {
//        System.out.println(id);
//        return null;
//    }
}
