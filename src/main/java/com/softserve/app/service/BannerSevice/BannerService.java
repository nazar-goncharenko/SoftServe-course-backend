package com.softserve.app.service.BannerSevice;

import com.softserve.app.dto.BannerDTO;
import com.softserve.app.dto.SportCategoryDTO;
import com.softserve.app.models.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {

    List<BannerDTO> listAll();

    BannerDTO findById(Long bannerId);

    List<BannerDTO> findByTitle(String name);

    List<BannerDTO> findAllByCategory(String categoryName);

    List<BannerDTO> findAllowedByCategory(String categoryName);

    List<SportCategoryDTO> getPredefinedCategories();

    List<BannerDTO> getOpen();

    List<BannerDTO> getClosed();

    List<BannerDTO> getByStatus(Banner.Status status);

    void showPredefined(String categoryName);

    void hidePredefined(String categoryName);

    BannerDTO update(String title, MultipartFile img, Long id);

    void configure(BannerDTO bannerDTO);

    void create(String title, MultipartFile img);

    void delete(Long bannerId);
}
