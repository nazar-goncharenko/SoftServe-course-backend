package com.softserve.app.service.PhotoService;

import com.softserve.app.dto.PhotoDTO;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoServiceInterface {

    PhotoDTO savePhoto(String photoDTO, MultipartFile image);
    PhotoDTO showPhoto();
}
