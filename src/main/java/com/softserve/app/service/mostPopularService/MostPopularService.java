package com.softserve.app.service.mostPopularService;

import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.models.Article;
import com.softserve.app.models.SportCategory;
import com.softserve.app.service.articleService.ArticleService;
import com.softserve.app.service.configService.ConfigService;
import com.softserve.app.service.sportCategoryService.SportCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MostPopularService {

    private final ConfigService configService;

    private final ArticleService articleService;

    private final SportCategoryService sportCategoryService;

    public List<ArticleDTO> getMostPopular(String location){
        LocalDateTime date = configService.getTop().getPeriod().getPeriodDate();

        if(location.equals("home")) {
            List<ArticleDTO> a = articleService.getArticlesByDateAndCategoryTreeGroupByHistoriesOrderByCountOfHistoriesDesc(date,
                    sportCategoryService.getAll(),
                    PageRequest.of(0, 3)).stream()
                    .map(Article::ofDTO)
                    .collect(Collectors.toList());
            return articleService.getArticlesByDateAndCategoryTreeGroupByHistoriesOrderByCountOfHistoriesDesc(date,
                    sportCategoryService.getAll(),
                    PageRequest.of(0, 3)).stream()
                    .map(Article::ofDTO)
                    .collect(Collectors.toList());
        }

        SportCategory category = sportCategoryService.getById(Long.valueOf(location));

        List<SportCategory> childrenTree = articleService.getAllSportCategoryChild(category, category.getChildren().stream().collect(Collectors.toList()));

        List<Article> mostPopular = articleService
                .getArticlesByDateAndCategoryTreeGroupByHistoriesOrderByCountOfHistoriesDesc(date,
                        childrenTree,
                        PageRequest.of(0,3));

        return mostPopular.stream().map(Article::ofDTO).collect(Collectors.toList());
    }
}
