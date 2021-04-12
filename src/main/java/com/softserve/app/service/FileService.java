package com.softserve.app.service;

import com.softserve.app.constant.BannerConstant;
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

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class FileService implements FileServiceInterface {

    // directory for uploaded images (you can change it in application.properties)
    @Value("${img.path}")
    private String uploadPath;

    @Override
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
                log.info(e.getMessage());
                throw new SportHubException(BannerConstant.IMAGE_IS_NOT_UPLOADED.getMessage(), 400);
            }
        }
    }

}
