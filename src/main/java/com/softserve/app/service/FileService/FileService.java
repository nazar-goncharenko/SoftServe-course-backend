package com.softserve.app.service.FileService;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveImg(MultipartFile img);
    String saveVideo(MultipartFile video);
}