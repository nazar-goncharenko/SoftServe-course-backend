package com.softserve.app.service;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.exception.SportHubException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class FileService implements FileServiceInterface {

    // directory for uploaded images (you can change it in application.properties)
    @Value("${img.path}")
    private String imgUploadPath;

    @Value("${img.video}")
    private String videoUploadPath;

    @Override
    public String saveImg(MultipartFile img){
        // if directory doesn't exist it will be created
        File uploadDir = new File(imgUploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String filename = uploadDir + "/" + UUID.randomUUID().toString();

        // only image file can be uploaded
        String mimetype= new MimetypesFileTypeMap()
                .getContentType(img.getOriginalFilename());

        String type = mimetype.split("/")[0];
        if(!type.equals("image"))
            throw new SportHubException(SportHubConstant.FILES_NOT_IMAGE.getMessage(), 400);
        else {
            // images with the same name will replace existing ones
            try {
                Files.copy(img.getInputStream(),
                        Path.of(filename),
                        StandardCopyOption.REPLACE_EXISTING);
                return filename;
            } catch (IOException e) {
                log.info(e.getMessage());
                throw new SportHubException(SportHubConstant.FILES_IMAGE_IS_NOT_UPLOADED.getMessage(), 400);
            }
        }
    }

}
