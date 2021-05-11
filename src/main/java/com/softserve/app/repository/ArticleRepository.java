package com.softserve.app.repository;

import com.softserve.app.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findArticleByTitleContainsOrDescriptionContainsIgnoreCase(String searchTitleQuery, String searchDescriptionQuery);

    List<Article> findAllByCategoryNameIgnoreCase(String categoryName);

}
