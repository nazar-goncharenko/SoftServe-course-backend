package com.softserve.app.service.fileService;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public String saveImg(MultipartFile img);

    public String saveVideo(MultipartFile video);
}
