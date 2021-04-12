package com.softserve.app.service;

import com.softserve.app.constant.VideoConstant;
import com.softserve.app.dto.VideoDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.Video;
import com.softserve.app.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;


    public List<VideoDTO> getAll(){
        return videoRepository.findAll().stream().map(Video::ofDTO).collect(Collectors.toList());
    }

    public VideoDTO getById(Long id){
        return videoRepository.findById(id).orElseThrow(() ->
                new SportHubException(VideoConstant.VIDEO_NOT_FOUND.getMessage(), 404))
                .ofDTO();
    }

    public boolean save(VideoDTO videoDTO, MultipartFile file){
        System.out.println(file);
        if(videoDTO.isUploaded()){
            System.out.println(file);
        }
        else{
            System.out.println(videoDTO.getUrl());
        }
        return true;
    }
}
