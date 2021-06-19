package com.example.demo.service;

import com.example.demo.dto.ArticleDTO;
import com.example.demo.entity.Article;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class ArticleMapper {

    public ArticleDTO toDTO(Article entity) {
        return ArticleDTO.newBuilder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .tags(entity.getTags().stream().map(t -> t.getTagId().getTag()).collect(toList()))
                .build();
    }

    public Article toEntity(ArticleDTO dto) {
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        dto.getTags().forEach(article::addTag);
        return article;
    }

}
