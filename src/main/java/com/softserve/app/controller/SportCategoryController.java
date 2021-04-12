package com.softserve.app.controller;

import com.softserve.app.dto.SportCategoryDTO;
import com.softserve.app.service.SportCategoryService.SportCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/sportCategory")
public class SportCategoryController {

    private final SportCategoryService sportCategoryService;


    @GetMapping("/basicNavigationList")
    public List<SportCategoryDTO> getAll(){
        return sportCategoryService.getBasicNavigationList();
    }
}
