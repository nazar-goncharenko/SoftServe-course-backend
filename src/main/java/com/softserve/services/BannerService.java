package com.softserve.services;

import com.softserve.Repositories.BannerRepository;
import com.softserve.app.models.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class BannerService {

    @Autowired
    BannerRepository bannerRepository;

    // directory for uploaded images (you can change it in application.properties)
    @Value("${upload.path}")
    private String uploadPath;

    public List<Banner> bannersList(){
        return bannerRepository.findAll();
    }

    public Banner create(String name, MultipartFile file){
        Banner banner = new Banner();
        banner.setName(name);
        banner.setOpen(false);

        // if directory doesn't exist it will be created
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String destinationfilename = uploadDir + "/" + file.getOriginalFilename();

        // only image file can be uploaded
        String mimetype= new MimetypesFileTypeMap().getContentType(destinationfilename);
        String type = mimetype.split("/")[0];
        if(!type.equals("image"))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid file");
        else {
            // images with the same name will replace existing ones
            try {
                Files.copy(file.getInputStream(),
                        Path.of(destinationfilename),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            banner.setImgPath(file.getOriginalFilename());
        }
        return bannerRepository.save(banner);
    }

}
