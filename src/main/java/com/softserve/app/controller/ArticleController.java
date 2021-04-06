package com.softserve.app.controller;

import com.softserve.app.constant.ArticleConstant;
import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.models.Article;
import com.softserve.app.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public List<Article> index() {
        return articleService.listArticles();
    }

    @PostMapping()
    public ResponseEntity<String> newArticle(@RequestBody ArticleDTO articleDto) {
        articleService.createArticle(articleDto);
        return ResponseEntity.ok(ArticleConstant.CREATED_SUCCESSFULLY.getMessage());
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDto) {
        articleService.updateArticle(id, articleDto);
        return ResponseEntity.ok(ArticleConstant.UPDATED_SUCCESSFULLY.getMessage());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok(ArticleConstant.DELETED_SUCCESSFULLY.getMessage());
    }

    @GetMapping("/search")
    public List<Article> search(@RequestParam String q) {
        System.out.println(q);
        return articleService.searchArticles(q);
    }

    @GetMapping("/filter_by")
    public List<Article> filterArticlesByCategory(@RequestParam String category) {
        System.out.println("Filter param is " + category);
        return articleService.searchArticlesByCategory(category);
    }
    
}
