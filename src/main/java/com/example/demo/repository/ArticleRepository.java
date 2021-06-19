package com.example.demo.repository;


import com.example.demo.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select a from Article a where a.title like %:title%")
    List<Article> findByTitle(@Param("title") String title);

}
