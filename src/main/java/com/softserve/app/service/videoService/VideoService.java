package com.softserve.app.service.videoService;

import com.softserve.app.dto.VideoDTO;
import com.softserve.app.models.Comment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    List<VideoDTO> getAll();

    List<VideoDTO> getPublished();

    VideoDTO getById(Long id);

    VideoDTO saveVideo(MultipartFile file);

    VideoDTO save(MultipartFile file, String videoDTO);

    VideoDTO update(MultipartFile file, String videoDTO);

    void delete(Long id);

    void addComment(Long id, Comment comment);
}
