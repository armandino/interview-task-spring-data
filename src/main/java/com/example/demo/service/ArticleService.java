package com.example.demo.service;

import com.example.demo.dto.ArticleDTO;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final BlackListService blackListService;

    public ArticleService(final ArticleRepository articleRepository,
                          final ArticleMapper articleMapper,
                          final BlackListService blackListService) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.blackListService = blackListService;
    }

    public Optional<ArticleDTO> getById(Long id) {
        return articleRepository.findById(id).map(articleMapper::toDTO);
    }

    public List<ArticleDTO> findByTitle(String title) {
        return articleRepository.findByTitle(title)
                .stream()
                .map(articleMapper::toDTO)
                .collect(toList());
    }

    @Transactional
    public Long create(ArticleDTO dto) {
        validateContent(dto.getContent());
        Article saved = articleRepository.save(articleMapper.toEntity(dto));
        return saved.getId();
    }

    @Transactional
    public void update(Long id, ArticleDTO dto) {
        validateContent(dto.getContent());

        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        dto.getTags().forEach(tagValue -> {
            if (!article.hasTag(tagValue))
                article.addTag(tagValue);
        });

        articleRepository.save(article);
    }

    private void validateContent(String content) {
        if (blackListService.containsBlackListedWord(content))
            throw new RuntimeException("Article contains blacklisted word");
    }

}
