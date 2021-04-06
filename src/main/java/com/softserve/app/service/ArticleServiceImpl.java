package com.softserve.app.service;

import com.softserve.app.constant.ArticleConstant;
import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.exception.ArticleNotFoundException;
import com.softserve.app.models.Article;
import com.softserve.app.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor // Required arguments are final fields and fields with constraints such as @NonNull
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public List<Article> listArticles() {
        return articleRepository.findAll();
    }

    @Override
    public void createArticle(ArticleDTO articleDto) {
        articleRepository.save(Article.builder()
                .title(articleDto.getTitle())
                .text(articleDto.getText())
                .category(articleDto.getCategory())
                .build());
    }

    @Override
    public void updateArticle(Long id, ArticleDTO articleDto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(ArticleConstant.ARTICLE_NOT_FOUND.getMessage()));
        log.info(article + "was found");
        log.info("Changing " + article.getTitle() + " to " + articleDto.getTitle());

        article.setTitle(articleDto.getTitle());
        article.setText(articleDto.getText());
        article.setCategory(articleDto.getCategory());
        articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(ArticleConstant.ARTICLE_NOT_FOUND.getMessage()));
        articleRepository.delete(article);
    }

    @Override
    public Article getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(ArticleConstant.ARTICLE_NOT_FOUND.getMessage()));
        return article;
    }

    @Override
    public List<Article> searchArticles(String searchQuery) {
        log.info("ArticleServiceImpl searchQuery: " + searchQuery);
        List<Article> articles = articleRepository.findArticleByTitleContainsOrTextContainsIgnoreCase(searchQuery, searchQuery);
        log.info(articles.toString());

        return articleRepository.findArticleByTitleContainsOrTextContainsIgnoreCase(searchQuery, searchQuery);
    }

    @Override
    public List<Article> searchArticlesByCategory(String categoryName) {
        /* можна виловлювати, що прийшло з квері параметрів і чи виконався метод репозиторію */
        log.info("ArticleServiceImpl categoryName: " + categoryName);
        List<Article> articles = articleRepository.findArticleByCategoryNameIgnoreCase(categoryName);
        log.info(articles.toString());

        return articleRepository.findArticleByCategoryNameIgnoreCase(categoryName);
    }
}
