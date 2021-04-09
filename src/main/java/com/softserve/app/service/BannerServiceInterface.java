package com.softserve.app.service;

import com.softserve.app.dto.BannerDTO;
import com.softserve.app.models.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerServiceInterface {

    List<BannerDTO> listAll();

    BannerDTO findById(Long bannerId);

    List<BannerDTO> findByTitle(String name);

    List<BannerDTO> findAllByCategory(String categoryName);

    List<BannerDTO> findAllowedByCategory(String categoryName);

    List<BannerDTO> getOpen();

    List<BannerDTO> getClosed();

    void showPredefined(String categoryName);

    void hidePredefined(String categoryName);

    void update(BannerDTO bannerDTO, MultipartFile img);

    void configure(BannerDTO bannerDTO);

    void create(BannerDTO bannerDTO, MultipartFile img);

    void delete(Long bannerId);
}
