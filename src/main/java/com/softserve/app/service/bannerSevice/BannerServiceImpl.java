package com.softserve.app.service.bannerSevice;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.BannerDTO;
import com.softserve.app.dto.SportCategoryDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.Banner;
import com.softserve.app.models.SportCategory;
import com.softserve.app.repository.BannerRepository;
import com.softserve.app.repository.SportCategoryRepository;
import com.softserve.app.service.fileService.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@AllArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    private final SportCategoryRepository sportCategoryRepository;

    private final FileService fileService;

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
        List<BannerDTO> banners = new ArrayList<BannerDTO>();
        if(categoryFromDb.isShowBanners()) {
            banners = bannerRepository.findAllByCategoryNameIgnoreCase(categoryName).stream()
                    .filter((b) -> b.getStatus().equals(Banner.Status.PUBLISHED))
                    .map(Banner::convertToDTO)
                    .collect(Collectors.toList());
        }
        return banners;
    }

    @Override
    public List<BannerDTO> findShownPredefined(){
        List<SportCategory> predefined = sportCategoryRepository.findByIsPredefinedTrue();
        List<BannerDTO> banners = new ArrayList<BannerDTO>();
        for (SportCategory category: predefined) {
            banners = Stream.of(banners, findAllowedByCategory(category.getName()))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        if(banners.isEmpty())
            throw new SportHubException(SportHubConstant.BANNER_NOT_FOUND.getMessage(), 400);
        return banners;
    }

    // list of banners related to chosen category and shown predefined banners
    @Override
    public List<BannerDTO> getUserSide(Long categoryId){
        SportCategory categoryFromDb = sportCategoryRepository.findByIdEquals(categoryId);
        if (categoryFromDb.isPredefined())
            return  findShownPredefined();
        return Stream.of(findAllowedByCategory(categoryFromDb.getName()), findShownPredefined())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
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

    @Override
    public List<BannerDTO> getByStatus(Banner.Status status){
        return bannerRepository.findByStatusEquals(status).stream()
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
    public boolean titleIsValid(String title){
        return (bannerRepository.findByTitleEquals(title)==null && title!= null);
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
        bannerFromDb.setCategory(bannerDTO.getCategory() != null ? sportCategoryRepository.findByName(bannerDTO.getCategory().getName()): bannerFromDb.getCategory());
        bannerRepository.save(bannerFromDb).convertToDTO();
    }

    @Override
    public void delete(Long bannerId){
        if (bannerRepository.findById(bannerId).isEmpty())
            throw new SportHubException(SportHubConstant.BANNER_NOT_FOUND.getMessage(), 400);
        bannerRepository.deleteById(bannerId);
    }

}
