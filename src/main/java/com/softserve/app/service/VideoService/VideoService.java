package com.softserve.app.service.VideoService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.VideoDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.Video;
import com.softserve.app.repository.VideoRepository;
import com.softserve.app.service.ConverterService.ConverterService;
import com.softserve.app.service.FileService.FileServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    private final FileServiceInterface fileService;

    private final ConverterService converterService;


    public List<VideoDTO> getAll() {
        return videoRepository.findAll().stream().map(Video::ofDTO).collect(Collectors.toList());
    }

    public VideoDTO getById(Long id) {
        return videoRepository.findById(id).orElseThrow(() ->
                new SportHubException(SportHubConstant.VIDEO_NOT_FOUND.getMessage(), 404))
                .ofDTO();
    }

    public VideoDTO saveVideo(MultipartFile file) {
        Video video = new Video();

        video.setPublish(false);
        video.setUploaded(true);
        video.setUrl(fileService.saveVideo(file));

        return videoRepository.save(video).ofDTO();
    }

    public VideoDTO save(MultipartFile file, String videoDTO) {
        VideoDTO dto = converterService.convertStringToClass(videoDTO, VideoDTO.class);
        if(file != null){
            dto.setUrl(fileService.saveVideo(file));
        }
        return videoRepository.save(Video.builder()
                .url(dto.getUrl())
                .title(dto.getTitle())
                .isUploaded(dto.isUploaded())
                .isPublish(dto.isPublish())
                .build()).ofDTO();
    }
}

