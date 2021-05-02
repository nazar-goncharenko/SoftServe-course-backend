package com.softserve.app.service.FileService;

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

    @Value("${video.path}")
    private String videoUploadPath;

    @Override
    public String saveImg(MultipartFile img){
        // if directory doesn't exist it will be created
        File uploadDir = new File(imgUploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        //String filename = uploadDir + "/" + UUID.randomUUID().toString();
        String filename = UUID.randomUUID().toString() + ".png";
        String fileDir = uploadDir + "/" + filename;

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
                        Path.of(fileDir),
                        StandardCopyOption.REPLACE_EXISTING);
                return filename;
            } catch (IOException e) {
                log.info(e.getMessage());
                throw new SportHubException(SportHubConstant.FILE_LOADING_EXCEPTION.getMessage(), 400);
            }
        }
    }

    @Override
    public String saveVideo(MultipartFile video) {
        File uploadDir = new File(videoUploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String fileUrl = UUID.randomUUID().toString();
        String filename = uploadDir + "\\" + fileUrl;

        // only image file can be uploaded
        String mimetype= new MimetypesFileTypeMap()
                .getContentType(video.getOriginalFilename());

        String type = mimetype.split("/")[0];
        System.out.println(type);
        if(!type.equals("application"))
            throw new SportHubException(SportHubConstant.FILES_NOT_VIDEO.getMessage(), 400);
        else {
            // images with the same name will replace existing ones
            try {
                Files.copy(video.getInputStream(),
                        Path.of(filename),
                        StandardCopyOption.REPLACE_EXISTING);
                return fileUrl;
            } catch (IOException e) {
                log.info(e.getMessage());
                throw new SportHubException(SportHubConstant.FILE_LOADING_EXCEPTION.getMessage(), 400);
            }
        }
    }

}
