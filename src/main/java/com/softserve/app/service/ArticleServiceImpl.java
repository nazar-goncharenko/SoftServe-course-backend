package com.softserve.app.service;

import com.softserve.app.constant.ArticleConstant;
import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.exception.ArticleNotFoundException;
import com.softserve.app.models.Article;
import com.softserve.app.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        System.out.println(article + "was found");
        System.out.println("changing " + article.getTitle() + " to " + articleDto.getTitle());
        article.setTitle(articleDto.getTitle());
        article.setText(articleDto.getText());
        article.setCategory(articleDto.getCategory());
        articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Long id) {
        /* не кидає ексепшна у разі якщо не знаходить */
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(ArticleConstant.ARTICLE_NOT_FOUND.getMessage()));
        articleRepository.delete(article);
    }

    @Override
    public Article getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(ArticleConstant.ARTICLE_NOT_FOUND.getMessage()));
        return article;
        /*Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            return article.get();
        } else {
            return null;
        }*/
    }

    @Override
    public List<Article> searchArticles(String searchQuery) {
        return articleRepository.findByKeywords(searchQuery);
    }

    @Override
    public List<Article> searchArticlesByCategory(String searchQuery) {
        System.out.println("ArticleServiceImpl searchQuery: " + searchQuery);
        List<Article> a = articleRepository.findByCategory(searchQuery);
        System.out.println(a.toString());

        return articleRepository.findByCategory(searchQuery);
    }
}
