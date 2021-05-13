package com.softserve.app.service.photoService;

import com.softserve.app.dto.PhotoDTO;
import com.softserve.app.models.PhotoOfTheDay;
import com.softserve.app.repository.PhotoRepository;
import com.softserve.app.service.converterService.ConverterService;
import com.softserve.app.service.fileService.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final FileService fileService;
    private final ConverterService converterService;

    public PhotoDTO savePhoto(String photoDTO, MultipartFile image) {

        PhotoDTO photo = converterService.convertStringToClass(photoDTO, PhotoDTO.class);
        photo.setPhotoUrl(fileService.saveImg(image));

        PhotoOfTheDay newPhoto = photo.fromDTO();
        photoRepository.save(newPhoto);

        return newPhoto.toDTO();
    }

}
