package com.softserve.app.service.ArticleService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.Article;
import com.softserve.app.repository.ArticleRepository;
import com.softserve.app.service.ConverterService.ConverterService;
import com.softserve.app.service.FileService.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor // Required arguments are final fields and fields with constraints such as @NonNull
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final FileService fileService;

    private final ConverterService converterService;

    @Override
    public List<ArticleDTO> listArticles() {
        return articleRepository.findAll().stream().map(Article::ofDTO).collect(Collectors.toList());
    }

    @Override
    public void createArticle(MultipartFile file, String articleDto) {
        ArticleDTO articleDTO = converterService.convertStringToClass(articleDto, ArticleDTO.class);

        if (file != null) {
            articleDTO.setImagePath(fileService.saveImg(file));
        }

        articleRepository.save(Article.builder()
                .title(articleDTO.getTitle())
                .description(articleDTO.getDescription())
                .category(articleDTO.getCategory())
                .imagePath(articleDTO.getImagePath())
                .build()).ofDTO();
    }

    @Override
    public void updateArticle(MultipartFile file, String articleDto) {
        ArticleDTO articleDTO = converterService.convertStringToClass(articleDto, ArticleDTO.class);

        Article article = articleRepository.findById(articleDTO.getId())
                .orElseThrow(() -> new SportHubException(SportHubConstant.ARTICLE_NOT_FOUND.getMessage(), 404));
        article.setTitle(articleDTO.getTitle());
        article.setImagePath(fileService.saveImg(file));
        article.setDescription(articleDTO.getDescription());
        article.setCategory(articleDTO.getCategory());
        articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new SportHubException(SportHubConstant.ARTICLE_NOT_FOUND.getMessage(), 404));
        articleRepository.delete(article);
    }

    @Override
    public ArticleDTO getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new SportHubException(SportHubConstant.ARTICLE_NOT_FOUND.getMessage(), 404))
                .ofDTO();
    }

    @Override
    public List<ArticleDTO> searchArticles(String searchQuery) {
        return articleRepository
                .findArticleByTitleContainsOrDescriptionContainsIgnoreCase(searchQuery, searchQuery)
                .stream()
                .map(Article::ofDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> searchArticlesByCategory(String categoryName) {
        return articleRepository.findAllByCategoryNameIgnoreCase(categoryName)
                .stream()
                .map(Article::ofDTO)
                .collect(Collectors.toList());
    }

}
