package com.softserve.app.controller;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.service.ArticleService.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public List<ArticleDTO> findAll() {
        return articleService.listArticles();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping()
    public ResponseEntity<String> create(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam(name = "articleDto") String articleDto
    ) {
        articleService.createArticle(file, articleDto);
        return ResponseEntity.ok(SportHubConstant.ARTICLE_CREATED_SUCCESSFULLY.getMessage());
    }

    @GetMapping("/{id}")
    public ArticleDTO getById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    // PASS ID VIA DTO OR PATHVARIABLE?
    @PutMapping()
    public ResponseEntity<String> edit(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam(name = "articleDto") String articleDto
    ) {
        System.out.println(articleDto);
        articleService.updateArticle(file, articleDto);
        return ResponseEntity.ok(SportHubConstant.ARTICLE_UPDATED_SUCCESSFULLY.getMessage());
    }

    @DeleteMapping()
    public ResponseEntity<String> delete(@RequestParam Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok(SportHubConstant.ARTICLE_DELETED_SUCCESSFULLY.getMessage());
    }

    @GetMapping("/search")
    public List<ArticleDTO> search(@RequestParam String q) {
        return articleService.searchArticles(q);
    }

    /* filterBy?category=nba */
    @GetMapping("/filterBy")
    public List<ArticleDTO> filterByCategory(@RequestParam String category) {
        return articleService.searchArticlesByCategory(category);
    }
}
