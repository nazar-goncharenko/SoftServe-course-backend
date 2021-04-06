package com.softserve.app.repository;

import com.softserve.app.models.Article;
import com.softserve.app.models.SportCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    /*
    @Query("SELECT a FROM Article a WHERE a.title LIKE %?1%"
            + " OR a.text LIKE %?1%")
    List<Article> findByKeywords(String searchQuery);

    @Query("select a from Article a"
            + " inner join SportCategory sc on a.category.id = sc.id "
            + " where sc.name = ?1")
    List<Article> findByCategory(String searchQuery);
     */

    List<Article> findArticleByTitleContainsOrTextContainsIgnoreCase(String searchTitleQuery, String searchDescriptionQuery);

    List<Article> findArticleByCategoryNameIgnoreCase(String categoryName);

}
