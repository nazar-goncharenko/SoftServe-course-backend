package com.softserve.app.service.ArticleService;

import com.softserve.app.constant.ArticleConstant;
import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.Article;
import com.softserve.app.repository.ArticleRepository;
import com.softserve.app.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor // Required arguments are final fields and fields with constraints such as @NonNull
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final FileService fileService;

    @Override
    public List<Article> listArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article createArticle(ArticleDTO articleDto) {
        return articleRepository.save(Article.builder()
                .title(articleDto.getTitle())
                .description(articleDto.getDescription())
                .category(articleDto.getCategory())
                .build());
    }

    @Override
    public void updateArticle(Long id, ArticleDTO articleDto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new SportHubException(ArticleConstant.ARTICLE_NOT_FOUND.getMessage(), 404));
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setCategory(articleDto.getCategory());
        articleRepository.save(article);
    }

    @Override
    public void updateArticleImage(Long id, MultipartFile multipartFile) {
        log.info("updateArticleImage()");
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new SportHubException(ArticleConstant.ARTICLE_NOT_FOUND.getMessage(), 404));
        articleRepository.delete(article);
    }

    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new SportHubException(ArticleConstant.ARTICLE_NOT_FOUND.getMessage(), 404));
    }

    @Override
    public List<Article> searchArticles(String searchQuery) {
        log.info("ArticleServiceImpl searchQuery: {}", searchQuery);
        List<Article> articles = articleRepository.findArticleByTitleContainsOrDescriptionContainsIgnoreCase(searchQuery, searchQuery);
        log.info(articles.toString());

        return articleRepository.findArticleByTitleContainsOrDescriptionContainsIgnoreCase(searchQuery, searchQuery);
    }

    @Override
    public List<Article> searchArticlesByCategory(String categoryName) {
        /* можна виловлювати, що прийшло з квері параметрів і чи виконався метод репозиторію */
        log.info("ArticleServiceImpl categoryName: {}", categoryName);
        List<Article> articles = articleRepository.findArticleByCategoryNameIgnoreCase(categoryName);
        log.info(articles.toString());

        return articleRepository.findArticleByCategoryNameIgnoreCase(categoryName);
    }
}
