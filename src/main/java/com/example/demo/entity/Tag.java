package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "tag")
public class Tag {

    @EmbeddedId
    private TagId tagId;

    @MapsId("articleId")
    @ManyToOne
    private Article article;


    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public TagId getTagId() {
        return tagId;
    }

    public void setTagId(TagId tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return String.format("TagId{%s}", tagId);
    }
}
