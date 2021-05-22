package com.softserve.app.repository;

import com.softserve.app.models.Article;
import com.softserve.app.models.SportCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findArticleByTitleContainsOrDescriptionContainsIgnoreCase(String searchTitleQuery, String searchDescriptionQuery);

    List<Article> findAllByCategoryNameIgnoreCase(String categoryName);

    @Query(value = "select distinct a from Article a, in(a.histories) h where a.date >= :date and a.category in(:categories) group by h.article order by count(h.article) desc")
    List<Article> getArticlesByDateAndCategoryTreeGroupByHistoriesOrderByCountOfHistoriesDesc(@Param("date") LocalDateTime date,
                                                                                              @Param("categories") List<SportCategory> categories,
                                                                                              Pageable pageable);
}
