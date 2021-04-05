package com.softserve.app.services;

import com.softserve.app.repository.BannerRepository;
import com.softserve.app.models.Banner;
import com.softserve.app.repository.SportCategoryRepository;
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
import java.util.Optional;

@Service
public class BannerService {

    @Autowired
    BannerRepository bannerRepository;

    @Autowired
    SportCategoryRepository sportCategoryRepository;

    // directory for uploaded images (you can change it in application.properties)
    @Value("${upload.path}")
    private String uploadPath;

    public List<Banner> bannersList(){
        return bannerRepository.findAll();
    }

    public Banner create(Banner banner, MultipartFile img){
        banner.setName(banner.getName());
        banner.setOpen(true);
        banner.setImgPath(saveImg(img));
        return bannerRepository.save(banner);
    }

    public String delete(Long bannerId){
        if (bannerRepository.findById(bannerId).isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Banner doesn't exist");
        bannerRepository.deleteById(bannerId);
        return "Banner was deleted successfully";
    }

    // "update" means changing image and banner name
    public Optional<Banner> update(Banner banner, MultipartFile img, Long bannerId){
        return bannerRepository.findById(bannerId).map(bannerFromDB -> {
            bannerFromDB.setName(banner.getName() != null ? banner.getName() : bannerFromDB.getName());
            if(!img.isEmpty())
                bannerFromDB.setImgPath(saveImg(img));
            return bannerRepository.save(bannerFromDB);
        });
    }

    // "configure" means status and category setting
    // TODO fix category setting
    public Optional<Banner> configure(Banner banner, Long bannerId){
        return bannerRepository.findById(bannerId).map(bannerFromDB -> {
            bannerFromDB.setStatus(banner.getStatus() != null ? banner.getStatus() : bannerFromDB.getStatus());
            banner.setCategory(banner.getCategory() != null ? sportCategoryRepository.findByIdEquals(banner.getCategory().getId()) : bannerFromDB.getCategory());
            return bannerRepository.save(bannerFromDB);
        });
    }

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
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid file");
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
