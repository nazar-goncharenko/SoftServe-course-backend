package com.softserve.app.service.VideoService;

import com.softserve.app.dto.VideoDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface VideoService {

    List<VideoDTO>  getAll();
    List<VideoDTO> getPublished();
    VideoDTO getById(Long id);
    VideoDTO saveVideo(MultipartFile file);
    public VideoDTO save(MultipartFile file, String videoDTO);
    public VideoDTO update(MultipartFile file, String videoDTO);
    public void delete(Long id);
}
