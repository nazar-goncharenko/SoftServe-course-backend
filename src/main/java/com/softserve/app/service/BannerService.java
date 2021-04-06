package com.softserve.app.service;

import com.softserve.app.constant.BannerConstant;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.repository.BannerRepository;
import com.softserve.app.models.Banner;
import com.softserve.app.repository.SportCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class BannerService {

    @Autowired
    BannerRepository bannerRepository;

    @Autowired
    FileService fileService;

    @Autowired
    SportCategoryRepository sportCategoryRepository;

    public List<Banner> bannersList(){
        return bannerRepository.findAll();
    }

    public Banner create(Banner banner, MultipartFile img){
        banner.setName(banner.getName());
        banner.setOpen(true);
        banner.setImgPath(fileService.saveImg(img));
        return bannerRepository.save(banner);
    }

    public void delete(Long bannerId){
        if (bannerRepository.findById(bannerId).isEmpty())
            throw new SportHubException(BannerConstant.BANNER_NOT_FOUND.getMessage(), 400);
        bannerRepository.deleteById(bannerId);
        throw new SportHubException(BannerConstant.DELETED_SUCCESSFULLY.getMessage(), 200);
    }

    // "update" means changing image and banner name
    public Optional<Banner> update(Banner banner, MultipartFile img, Long bannerId){
        return bannerRepository.findById(bannerId).map(bannerFromDB -> {
            bannerFromDB.setName(banner.getName() != null ? banner.getName() : bannerFromDB.getName());
            if(!img.isEmpty())
                bannerFromDB.setImgPath(fileService.saveImg(img));
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
}
