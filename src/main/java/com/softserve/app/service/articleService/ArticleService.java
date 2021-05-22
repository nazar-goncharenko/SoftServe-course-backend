package com.softserve.app.service.articleService;

import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.models.Article;
import com.softserve.app.models.SportCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleService {

    List<ArticleDTO> listArticles();

    void createArticle(MultipartFile file, String articleDTO);

    void updateArticle(MultipartFile file, String articleDto);

    void deleteArticle(Long id);

    ArticleDTO getArticleById(Long id);

    List<ArticleDTO> searchArticles(String searchQuery);

    List<ArticleDTO> searchArticlesByCategory(String searchQuery);

    List<SportCategory> getAllSportCategoryChild(SportCategory category, List<SportCategory> children);

    List<Article> getArticlesByDateAndCategoryTreeGroupByHistoriesOrderByCountOfHistoriesDesc(@Param("date") LocalDateTime date,
                                                                                              @Param("categories") List<SportCategory> categories,
                                                                                              Pageable pageable);
}
