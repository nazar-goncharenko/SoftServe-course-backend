package com.softserve.app.service;


import com.softserve.app.models.SportCategory;
import com.softserve.app.repository.SportCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportCategoryService {
    @Autowired
    SportCategoryRepository sportCategoryRepo;


    public List<SportCategory> getParent(){
        return sportCategoryRepo.getAllByParentEquals(null);
    }
}
