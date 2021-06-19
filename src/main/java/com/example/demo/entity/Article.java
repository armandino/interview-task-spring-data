package com.example.demo.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "article")
    private Set<Tag> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public boolean hasTag(String tagValue) {
        return tagValue != null && tags.stream().anyMatch(tag -> tagValue.equals(tag.getTagId().getTag()));
    }

    public void addTag(String tagValue) {
        TagId tagId = new TagId();
        tagId.setTag(tagValue);

        Tag tag = new Tag();
        tag.setTagId(tagId);
        tag.setArticle(this);
        tags.add(tag);
    }

    @Override
    public String toString() {
        return String.format("Article{id=%d, title='%s', content='%s', tags=%s}", id, title, content, tags);
    }
}
