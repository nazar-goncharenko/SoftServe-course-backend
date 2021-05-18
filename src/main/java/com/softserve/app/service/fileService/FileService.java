package com.softserve.app.service.fileService;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveImg(MultipartFile img);
    String saveVideo(MultipartFile video);
}