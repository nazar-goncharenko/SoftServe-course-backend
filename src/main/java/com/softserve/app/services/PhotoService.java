package com.softserve.app.services;

import com.softserve.app.dto.PhotoDTO;
import com.softserve.app.models.PhotoOfTheDay;
import com.softserve.app.repositories.PhotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@AllArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    public static final String uploadDir = "C:\\Users\\User\\Documents\\Java Course\\SoftServe-course-backend\\src\\main\\resources\\photos";

    public PhotoOfTheDay savePhotoDTO(PhotoDTO photoDTO) {
        PhotoOfTheDay newPhoto = photoDTO.fromDTO();
        return photoRepository.save(newPhoto);
    }

    public PhotoDTO savePhoto(Long id, MultipartFile image) throws IOException {

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());

        PhotoOfTheDay photo = photoRepository.getOne(id);
        photo.setPhotoUrl(fileName);

        saveFile(uploadDir, fileName, image);

        PhotoOfTheDay newPhoto = photoRepository.save(photo);

        return newPhoto.toDTO();
    }

    public void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }


}
