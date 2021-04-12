package com.softserve.app.service.ArticleService;

import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.models.Article;

import java.util.List;

public interface ArticleService {

    List<Article> listArticles();

    void createArticle(ArticleDTO articleDto);

    void updateArticle(Long id, ArticleDTO articleDto);

    void deleteArticle(Long id);

    Article getArticleById(Long id);

    List<Article> searchArticles(String searchQuery);

    List<Article> searchArticlesByCategory(String searchQuery);

}
