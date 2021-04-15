package com.softserve.app.controller;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.models.Article;
import com.softserve.app.service.ArticleService.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public @ResponseBody
    ResponseEntity<List<Article>> findAll() {
        return new ResponseEntity<>(articleService.listArticles(), HttpStatus.OK);
    }

    /* without image */
    @PostMapping()
    public @ResponseBody ResponseEntity<Article> create(@RequestBody ArticleDTO articleDto) {
        return new ResponseEntity<>(articleService.createArticle(articleDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<Article> getById(@PathVariable Long id) {
        return new ResponseEntity<>(articleService.getArticleById(id), HttpStatus.OK);
    }

    /* without image */
    @PutMapping()
    public ResponseEntity<String> edit(@RequestBody ArticleDTO articleDto) {
        articleService.updateArticle(articleDto.getId(), articleDto);
        return ResponseEntity.ok(SportHubConstant.ARTICLE_UPDATED_SUCCESSFULLY.getMessage());
    }

    /* separate endpoint for image upload */
    @PutMapping("/{id}/image")
    public ResponseEntity<String> updateImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile multipartFile
    ) {
        articleService.updateArticleImage(id, multipartFile);
        return ResponseEntity.ok(SportHubConstant.ARTICLE_IMAGE_UPDATED_SUCCESSFULLY.getMessage());
    }

    @DeleteMapping()
    public ResponseEntity<String> delete(@RequestParam Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok(SportHubConstant.ARTICLE_DELETED_SUCCESSFULLY.getMessage());
    }

    @GetMapping("/search")
    public @ResponseBody
    ResponseEntity<List<Article>> search(@RequestParam String q) {
        return new ResponseEntity<>(articleService.searchArticles(q), HttpStatus.OK);
    }

    @GetMapping("/filterBy")
    public @ResponseBody
    ResponseEntity<List<Article>> filterByCategory(@RequestParam String category) {
        return new ResponseEntity<>(articleService.searchArticlesByCategory(category), HttpStatus.OK);
    }

}
