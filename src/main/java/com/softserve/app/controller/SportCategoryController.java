package com.softserve.app.controller;

import com.softserve.app.models.SportCategory;
import com.softserve.app.service.SportCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/sportCategory")
public class SportCategoryController {
    @Autowired
    SportCategoryService sportCategoryService;


    @GetMapping("/nullParent")
    public List<SportCategory> getAll(){
        return sportCategoryService.getNullParent();
    }
}
