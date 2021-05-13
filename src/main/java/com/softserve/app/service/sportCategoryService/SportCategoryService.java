package com.softserve.app.service.sportCategoryService;


import com.softserve.app.dto.SportCategoryDTO;
import com.softserve.app.models.SportCategory;
import com.softserve.app.repository.SportCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SportCategoryService {

    private final SportCategoryRepository sportCategoryRepo;


    public List<SportCategoryDTO> getBasicNavigationList() {
        return sportCategoryRepo.getAllByParentEquals(null).stream()
                .map(SportCategory::ofDTO)
                .collect(Collectors.toList());
    }
}
