package com.example.demo.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArticleDTO {

    private final String title;
    private final String content;
    private final List<String> tags;

    private ArticleDTO(Builder builder) {
        title = builder.title;
        content = builder.content;
        tags = Collections.unmodifiableList(builder.tags);
    }

    public static Builder newBuilder(String title, String content, List<String> tags) {
        return new Builder(title, content, tags);
    }

    public static Builder newBuilder(ArticleDTO copy) {
        return new Builder(copy.title, copy.content, copy.tags);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTags() {
        return tags;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String title;
        private String content;
        private List<String> tags;

        private Builder() {
        }

        private Builder(String title, String content, List<String> tags) {
            this.title = title;
            this.content = content;
            this.tags = tags;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
            return this;
        }

        public Builder tags(List<String> val) {
            tags = val;
            return this;
        }

        public ArticleDTO build() {
            return new ArticleDTO(this);
        }

        public Builder tags(String... vals) {
            tags = Arrays.asList(vals);
            return this;
        }
    }

    @Override
    public String toString() {
        return String.format("ArticleDTO{title='%s', content='%s', tags=%s}", title, content, tags);
    }
}
