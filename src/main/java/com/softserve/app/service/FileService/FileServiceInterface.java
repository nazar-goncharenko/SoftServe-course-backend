package com.softserve.app.service.FileService;

import org.springframework.web.multipart.MultipartFile;

public interface FileServiceInterface {

    public String saveImg(MultipartFile img);

    public String saveVideo(MultipartFile video);
}
