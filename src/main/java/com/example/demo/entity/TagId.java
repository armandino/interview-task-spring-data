package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TagId implements Serializable {

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "tag")
    private String tag;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagId)) return false;
        TagId tagId = (TagId) o;
        return Objects.equals(articleId, tagId.articleId) &&
                Objects.equals(tag, tagId.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, tag);
    }

    @Override
    public String toString() {
        return String.format("TagId{articleId=%d, tag='%s'}", articleId, tag);
    }
}
