package com.softserve.app.service.articleService;

import com.softserve.app.dto.ArticleDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {

    List<ArticleDTO> listArticles();

    void createArticle(MultipartFile file, String articleDTO);

    void updateArticle(MultipartFile file, String articleDto);

    void deleteArticle(Long id);

    ArticleDTO getArticleById(Long id);

    List<ArticleDTO> searchArticles(String searchQuery);

    List<ArticleDTO> searchArticlesByCategory(String searchQuery);

}
