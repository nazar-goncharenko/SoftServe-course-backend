package com.softserve.app.service.BannerSevice;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.BannerDTO;
import com.softserve.app.dto.SportCategoryDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.Banner;
import com.softserve.app.models.SportCategory;
import com.softserve.app.repository.BannerRepository;
import com.softserve.app.repository.SportCategoryRepository;
import com.softserve.app.service.FileService.FileServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

;


@Service
@AllArgsConstructor
public class BannerService implements BannerServiceInterface {

    private final BannerRepository bannerRepository;

    private final SportCategoryRepository sportCategoryRepository;

    private final FileServiceInterface fileService;

    // returns current date in format " 1 January 2021 "
    public String today(){
        String datePattern = "dd MMMM yyyy";
        LocalDateTime lastUpdated = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(datePattern);
        return lastUpdated.format(myFormatObj);
    }

    @Override
    public List<BannerDTO> listAll(){
        return bannerRepository.findAll().stream()
                .map(Banner::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BannerDTO findById(Long bannerId){
        Banner bannerFromDB = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new SportHubException(SportHubConstant.BANNER_NOT_FOUND.getMessage(), 400));
        return bannerFromDB.convertToDTO();
    }

    @Override
    public List<SportCategoryDTO> getPredefinedCategories(){
        return sportCategoryRepository.findByIsPredefinedTrue().stream()
                .map(SportCategory::ofDTO)
                .collect(Collectors.toList());
    }

    // list of all banners in category, allowed only for admins
    @Override
    public List<BannerDTO> findAllByCategory(String categoryName) {
        return bannerRepository.findAllByCategoryNameIgnoreCase(categoryName).stream()
                .map(Banner::convertToDTO)
                .collect(Collectors.toList());
    }

    // list of OPEN banners in category, which is returned only if admin has made it visible to users
    @Override
    public List<BannerDTO> findAllowedByCategory(String categoryName) {
        SportCategory categoryFromDb = sportCategoryRepository.findByName(categoryName);
        if(categoryFromDb.isShowBanners())
            return bannerRepository.findAllByCategoryNameIgnoreCase(categoryName).stream()
                    .filter((b)-> b.getStatus().equals(Banner.Status.NOT_PUBLISHED) || b.getStatus().equals(Banner.Status.PUBLISHED))
                    .map(Banner::convertToDTO)
                    .collect(Collectors.toList());
        else
            return null;
    }

    @Override
    public List<BannerDTO> findByTitle(String name){
        List<Banner> bannerFromDb = bannerRepository.findByTitleIgnoreCaseContaining(name);
        return bannerFromDb.stream()
                .map(Banner::convertToDTO)
                .collect(Collectors.toList());
    }

    // list of open banners ( with status "PUBLISHED" or "NOT_PUBLISHED" )
    @Override
    public List<BannerDTO> getOpen(){
        return bannerRepository.findByStatusNot(Banner.Status.CLOSED).stream()
                .map(Banner::convertToDTO)
                .collect(Collectors.toList());
    }

    // list of banners with status "CLOSED"
    @Override
    public List<BannerDTO> getClosed(){
        return bannerRepository.findByStatusEquals(Banner.Status.CLOSED).stream()
                .map(Banner::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void showPredefined(String categoryName){
        SportCategory categoryFromDb = sportCategoryRepository.findByName(categoryName);
        categoryFromDb.setShowBanners(true);
        sportCategoryRepository.save(categoryFromDb);
    }

    @Override
    public void hidePredefined(String categoryName){
        SportCategory categoryFromDb = sportCategoryRepository.findByName(categoryName);
        categoryFromDb.setShowBanners(false);
        sportCategoryRepository.save(categoryFromDb);
    }

    @Override
    public void create(String title, MultipartFile img){
        bannerRepository.save(Banner.builder()
                .title(title)
                .status(Banner.Status.NOT_PUBLISHED)
                .lastUpdated(today())
                .imgPath(fileService.saveImg(img))
                .build());
    }

    // "update" means changing image and banner name
    @Override
    public BannerDTO update(String title, MultipartFile img, Long id){
        Banner bannerFromDb = bannerRepository.findById(id)
                .orElseThrow(() -> new SportHubException(SportHubConstant.BANNER_NOT_FOUND.getMessage(), 400));
        bannerFromDb.setTitle(title != null ? title : bannerFromDb.getTitle());
        bannerFromDb.setImgPath(img!= null ? fileService.saveImg(img) : bannerFromDb.getImgPath());
        bannerFromDb.setLastUpdated(today());
        return bannerRepository.save(bannerFromDb).convertToDTO();
    }

    // "configure" means status and category setting
    @Override
    public void configure(BannerDTO bannerDTO){
        Banner bannerFromDb = bannerRepository.findById(bannerDTO.getId())
                .orElseThrow(() -> new SportHubException(SportHubConstant.BANNER_NOT_FOUND.getMessage(), 400));
        bannerFromDb.setStatus(bannerDTO.getStatus() != null ? bannerDTO.getStatus() : bannerFromDb.getStatus());
        bannerFromDb.setCategory(bannerDTO.getCategory() != null ? bannerDTO.getCategory() : bannerFromDb.getCategory());
        bannerRepository.save(bannerFromDb).convertToDTO();
    }

    @Override
    public void delete(Long bannerId){
        if (bannerRepository.findById(bannerId).isEmpty())
            throw new SportHubException(SportHubConstant.BANNER_NOT_FOUND.getMessage(), 400);
        bannerRepository.deleteById(bannerId);
    }

}
