package com.example.demo;

import com.example.demo.dto.ArticleDTO;
import com.example.demo.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ArticleService articleService;

    @Test
    void findByTitle() {
        ArticleDTO dto = articleDTOTemplate()
                .title("War and Peace")
                .build();

        articleService.create(dto);

        List<ArticleDTO> results = articleService.findByTitle("War");

        assertThat(results).hasSize(1).first().satisfies(
                persisted -> assertThat(persisted.getTitle()).isEqualTo(dto.getTitle()));
    }

    @Test
    void create() {
        ArticleDTO dto = articleDTOTemplate().build();
        Long id = articleService.create(dto);
        Optional<ArticleDTO> result = articleService.getById(id);

        assertThat(result).isPresent().get().satisfies(persisted -> {
            assertThat(persisted.getTitle()).isEqualTo(dto.getTitle());
            assertThat(persisted.getContent()).isEqualTo(dto.getContent());
            assertThat(persisted.getTags()).containsExactlyInAnyOrder(dto.getTags().toArray(new String[0]));
        });
    }

    @Test
    void createWithBlackListedWord() {
        assertThatThrownBy(() -> articleService.create(articleDTOTemplate().content("foo").build()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Article contains blacklisted word");
    }

    @Test
    void update() {
        ArticleDTO originalDTO = articleDTOTemplate().build();
        Long id = articleService.create(originalDTO);

        ArticleDTO updatedDTO = ArticleDTO.newBuilder(originalDTO)
                .content("new title")
                .content("new content")
                .tags("tag1", "new-tag2")
                .build();

        Set<String> expectedTags = new HashSet<>();
        expectedTags.addAll(originalDTO.getTags());
        expectedTags.addAll(updatedDTO.getTags());

        articleService.update(id, updatedDTO);

        Optional<ArticleDTO> result = articleService.getById(id);

        assertThat(result).isPresent().get().satisfies(presisted -> {
            assertThat(presisted.getTitle()).isEqualTo(updatedDTO.getTitle());
            assertThat(presisted.getContent()).isEqualTo(updatedDTO.getContent());
            assertThat(presisted.getTags()).containsExactlyInAnyOrder(expectedTags.toArray(new String[0]));
        });
    }

    private static ArticleDTO.Builder articleDTOTemplate() {
        return ArticleDTO.newBuilder()
                .title("some test title")
                .content("some test content")
                .tags("tag1", "tag2");
    }
}
