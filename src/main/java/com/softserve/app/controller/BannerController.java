package com.softserve.app.controller;

import com.softserve.app.models.Banner;
import com.softserve.app.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping (path = "/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/list")
    public List<Banner> allBanners() {
        return bannerService.bannersList();
    }

    // TODO should be available only for admins
    @PostMapping("/create")
    public Banner create(
            @RequestPart("banner")Banner banner,
            @RequestPart("file")MultipartFile file){
        return bannerService.create(banner, file);
    }

    // TODO should be available only for admins
    @PutMapping("/update/{bannerId}")
    public Optional<Banner> update(@PathVariable Long bannerId,
                                   @RequestPart("banner")Banner banner,
                                   @RequestPart(value = "file", required = false)MultipartFile file) {
        return bannerService.update(banner,file, bannerId);
    }

    // TODO should be available only for admins
    @PutMapping("/configure/{bannerId}")
    public Optional<Banner> configure(@PathVariable Long bannerId,
                                      @RequestPart("banner")Banner banner){
        return bannerService.configure(banner, bannerId);
    }

    // TODO should be available only for admins
    @DeleteMapping("/delete/{bannerId}")
    public void deleteBanner(@PathVariable Long bannerId){
        bannerService.delete(bannerId);
    }

}
