package com.softserve.app.controller;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.BannerDTO;
import com.softserve.app.dto.SportCategoryDTO;
import com.softserve.app.models.Banner;
import com.softserve.app.service.bannerSevice.BannerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping (path = "/banners")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BannerController {

    private final BannerService bannerService;

    // TODO should be available only for admins
    @GetMapping()
    public List<BannerDTO> getAll() {
        return bannerService.listAll();
    }

    // TODO should be available only for admins
    @GetMapping("/open")
    public List<BannerDTO> getOpen(){
        return bannerService.getOpen();
    }

    // TODO should be available only for admins
    @GetMapping("/closed")
    public List<BannerDTO> getClosed(){
        return bannerService.getClosed();
    }

    // TODO should be available only for admins
    @GetMapping("/sortByStatus")
    public List<BannerDTO> getByStatus(@RequestParam("status") Banner.Status status){
        return bannerService.getByStatus(status);
    }

    @GetMapping("/{bannerId}")
    public BannerDTO getOne(@PathVariable Long bannerId){
        return bannerService.findById(bannerId);
    }

    // TODO should be available only for admins
    @GetMapping("/filterAdminSide")
    public List<BannerDTO> filterByCategory(@RequestParam("category") String categoryName){
        return bannerService.findAllByCategory(categoryName);
    }

    // TODO should be available only for admins
    @GetMapping("/filterUserSide")
    public List<BannerDTO> filterAllowedByCategory(@RequestParam("category") String categoryName){
        return bannerService.findAllowedByCategory(categoryName);
    }

    @GetMapping("/userside/{categoryId}")
    public List<BannerDTO> userside(@PathVariable Long categoryId){
        return bannerService.getUserSide(categoryId);
    }

    // TODO should be available only for admins
    @GetMapping("/search")
    public List<BannerDTO> searchByTitle(@RequestParam("title") String title){
        return bannerService.findByTitle(title);
    }

    @GetMapping("/predefinedCategories")
    public List<SportCategoryDTO> getPredefinedCategories(){
        return bannerService.getPredefinedCategories();
    }

    // TODO should be available only for admins
    @PutMapping("/show")
    public ResponseEntity<String> showPredefined(@RequestParam("category") String categoryName){
        bannerService.showPredefined(categoryName);
        return ResponseEntity.ok(SportHubConstant.BANNER_SHOWN_SUCCESSFULLY.getMessage());
    }

    // TODO should be available only for admins
    @PutMapping("/hide")
    public ResponseEntity<String> hidePredefined(@RequestParam("category") String categoryName){
        bannerService.hidePredefined(categoryName);
        return ResponseEntity.ok(SportHubConstant.BANNER_HIDDEN_SUCCESSFULLY.getMessage());
    }

    // TODO should be available only for admins
    @PostMapping()
    public ResponseEntity<String> create(
            @RequestParam("title") String title,
            @RequestParam(value="file", required = false )MultipartFile file){
        if(file==null)
            return ResponseEntity.badRequest().body(SportHubConstant.BANNER_IMAGE_NOT_FOUND.getMessage());
        if(!bannerService.titleIsValid(title))
            return ResponseEntity.badRequest().body(SportHubConstant.BANNER_TITLE_IS_NOT_VALID.getMessage());
        bannerService.create(title, file);
        return ResponseEntity.ok(SportHubConstant.BANNER_CREATED_SUCCESSFULLY.getMessage());
    }

    // TODO should be available only for admins
    @PutMapping("/update/{bannerId}")
    public ResponseEntity<String> update(
            @PathVariable Long bannerId,
            @RequestParam("title") String title,
            @RequestParam(value = "file", required = false)MultipartFile file) {
        if(!bannerService.titleIsValid(title))
            return ResponseEntity.badRequest().body(SportHubConstant.BANNER_TITLE_IS_NOT_VALID.getMessage());
        bannerService.update(title, file, bannerId);
        return ResponseEntity.ok(SportHubConstant.BANNER_UPDATED_SUCCESSFULLY.getMessage());
    }

    // TODO should be available only for admins
    @PutMapping("/configure")
    public ResponseEntity<String> configure(@RequestBody BannerDTO bannerDTO){
        bannerService.configure(bannerDTO);
        return ResponseEntity.ok(SportHubConstant.BANNER_CONFIGURED_SUCCESSFULLY.getMessage());
    }

    // TODO should be available only for admins
    @DeleteMapping("/{bannerId}")
    public ResponseEntity<String> delete(@PathVariable Long bannerId){
        bannerService.delete(bannerId);
        return ResponseEntity.ok(SportHubConstant.BANNER_DELETED_SUCCESSFULLY.getMessage());
    }

}
