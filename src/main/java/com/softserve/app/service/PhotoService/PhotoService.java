package com.softserve.app.service.PhotoService;

import com.softserve.app.dto.PhotoDTO;
import com.softserve.app.models.PhotoOfTheDay;
import com.softserve.app.repository.PhotoRepository;
import com.softserve.app.service.FileService.FileServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final FileServiceInterface fileService;

    public PhotoOfTheDay savePhotoDTO(PhotoDTO photoDTO) {
        PhotoOfTheDay newPhoto = photoDTO.fromDTO();
        return photoRepository.save(newPhoto);
    }

    public PhotoDTO savePhoto(Long id, MultipartFile image) {

        PhotoOfTheDay photo = photoRepository.getOne(id);
        photo.setPhotoUrl(fileService.saveImg(image));

        PhotoOfTheDay newPhoto = photoRepository.save(photo);

        return newPhoto.toDTO();
    }

}
