package com.softserve.app.service.videoService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.VideoDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.Comment;
import com.softserve.app.models.Video;
import com.softserve.app.repository.VideoRepository;
import com.softserve.app.service.converterService.ConverterService;
import com.softserve.app.service.fileService.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VideoServiceImpl implements VideoService{

    private final VideoRepository videoRepository;

    private final FileService fileService;

    private final ConverterService converterService;


    public List<VideoDTO> getAll() {
        return videoRepository.findAll()
                .stream()
                .map(Video::ofDTO)
                .collect(Collectors.toList());
    }

    public List<VideoDTO> getPublished() {
        return videoRepository.getAllByPublishIsTrue()
                .stream()
                .map(Video::ofDTO)
                .collect(Collectors.toList());
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
        if (file != null) {
            dto.setUrl(fileService.saveVideo(file));
        }
        System.out.println(Video.builder()
                .url(dto.getUrl())
                .title(dto.getTitle())
                .uploaded(dto.isUploaded())
                .publish(dto.isPublish())
                .showComments(dto.isShowComments())
                .build());
        return videoRepository.save(Video.builder()
                .url(dto.getUrl())
                .title(dto.getTitle())
                .uploaded(dto.isUploaded())
                .publish(dto.isPublish())
                .showComments(dto.isShowComments())
                .build()).ofDTO();

    }

    public void addComment(Long id, Comment comment){
        Video video = videoRepository.findById(id).orElseThrow(() ->
                new SportHubException(SportHubConstant.VIDEO_NOT_FOUND.getMessage(), 404));
        video.addComment(comment);
        videoRepository.save(video);
    }

    public VideoDTO update(MultipartFile file, String videoDTO) {
        VideoDTO dto = converterService.convertStringToClass(videoDTO, VideoDTO.class);

        if (file != null) {
            dto.setUrl(fileService.saveVideo(file));
        }

        Video video = videoRepository.findById(dto.getId())
                .orElseThrow(() ->
                        new SportHubException(SportHubConstant.VIDEO_NOT_FOUND.getMessage(), 404));

        Video updated = video.setFromDTO(dto);
        return videoRepository.save(
                updated)
                .ofDTO();
    }

    public void delete(Long id) {
        videoRepository.delete(
                videoRepository.findById(id).orElseThrow(
                        () -> new SportHubException(SportHubConstant.VIDEO_NOT_FOUND.getMessage(), 404)
                )
        );
    }
}

