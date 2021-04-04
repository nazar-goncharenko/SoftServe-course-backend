package com.softserve.controllers;

import com.softserve.app.models.Banner;
import com.softserve.services.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file){
        return bannerService.create(name, file);
    }

}
