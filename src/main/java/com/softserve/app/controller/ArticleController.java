package com.softserve.app.controller;

import com.softserve.app.constant.ArticleConstant;
import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.models.Article;
import com.softserve.app.service.ArticleService.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public List<Article> findAll() {
        return articleService.listArticles();
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody ArticleDTO articleDto) {
        articleService.createArticle(articleDto);
        return ResponseEntity.ok(ArticleConstant.CREATED_SUCCESSFULLY.getMessage());
    }

    @GetMapping("/{id}")
    public Article getById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    @PutMapping()
    public ResponseEntity<String> edit(@RequestBody ArticleDTO articleDto) {
        articleService.updateArticle(articleDto.getId(), articleDto);
        return ResponseEntity.ok(ArticleConstant.UPDATED_SUCCESSFULLY.getMessage());
    }

    @DeleteMapping()
    public ResponseEntity<String> delete(@RequestParam Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok(ArticleConstant.DELETED_SUCCESSFULLY.getMessage());
    }

    @GetMapping("/search")
    public List<Article> search(@RequestParam String q) {
        return articleService.searchArticles(q);
    }

    @GetMapping("/filterBy")
    public List<Article> filterByCategory(@RequestParam String category) {
        return articleService.searchArticlesByCategory(category);
    }
    
}
