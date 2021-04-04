package com.softserve.app.services;

import com.softserve.app.models.PhotoOfTheDay;
import com.softserve.app.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    public PhotoOfTheDay savePhoto(PhotoOfTheDay photo, MultipartFile image) throws IOException {

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        photo.setPhotoUrl(fileName);

        String uploadDir = "C:\\Users\\User\\Documents\\Java Course\\SoftServe-course-backend\\src\\main\\resources\\photos";

        saveFile(uploadDir, fileName, image);

        return photoRepository.save(photo);
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
