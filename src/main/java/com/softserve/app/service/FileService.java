package com.softserve.app.service;

import com.softserve.app.constant.BannerConstant;
import com.softserve.app.exception.SportHubException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FileService implements FileServiceInterface {

    // directory for uploaded images (you can change it in application.properties)
    @Value("${upload.path}")
    private String uploadPath;

    public String saveImg(MultipartFile img){
        // if directory doesn't exist it will be created
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String filename = uploadDir + "/" + img.getOriginalFilename();

        // only image file can be uploaded
        String mimetype= new MimetypesFileTypeMap().getContentType(filename);
        String type = mimetype.split("/")[0];
        if(!type.equals("image"))
            throw new SportHubException(BannerConstant.NOT_IMAGE.getMessage(), 400);
        else {
            // images with the same name will replace existing ones
            try {
                Files.copy(img.getInputStream(),
                        Path.of(filename),
                        StandardCopyOption.REPLACE_EXISTING);
                return img.getOriginalFilename();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
