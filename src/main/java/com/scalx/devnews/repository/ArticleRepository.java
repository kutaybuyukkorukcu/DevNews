package com.scalx.devnews.repository;

import com.scalx.devnews.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    public Article findByTitle(String title);

    public Article findById(long id);

    public Article findByArticleLink(String link);
}
