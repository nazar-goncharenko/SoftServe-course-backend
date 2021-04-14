package com.softserve.app.service.ArticleService;

import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.models.Article;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {

    List<Article> listArticles();

    Article createArticle(ArticleDTO articleDto);

    void updateArticle(Long id, ArticleDTO articleDto);

    void updateArticleImage(Long id, MultipartFile multipartFile);

    void deleteArticle(Long id);

    Article getArticleById(Long id);

    List<Article> searchArticles(String searchQuery);

    List<Article> searchArticlesByCategory(String searchQuery);

}
