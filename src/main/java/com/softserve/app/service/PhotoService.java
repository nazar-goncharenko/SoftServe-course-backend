package com.softserve.app.service;

import com.softserve.app.dto.PhotoDTO;
import com.softserve.app.models.PhotoOfTheDay;
import com.softserve.app.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final FileService fileService;

    public PhotoOfTheDay savePhotoDTO(PhotoDTO photoDTO) {
        PhotoOfTheDay newPhoto = photoDTO.fromDTO();
        return photoRepository.save(newPhoto);
    }

    public PhotoDTO savePhoto(Long id, MultipartFile image) {

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());

        PhotoOfTheDay photo = photoRepository.getOne(id);
        photo.setPhotoUrl(fileName);

        fileService.saveImg(image);

        PhotoOfTheDay newPhoto = photoRepository.save(photo);

        return newPhoto.toDTO();
    }

}
