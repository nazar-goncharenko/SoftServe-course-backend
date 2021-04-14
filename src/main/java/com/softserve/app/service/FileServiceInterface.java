package com.softserve.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileServiceInterface {

    public String saveImg(MultipartFile img);
}
