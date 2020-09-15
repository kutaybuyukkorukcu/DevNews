package com.scalx.devnews.repository;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query(value = "SELECT * FROM likes", nativeQuery = true)
    public List<Article> findAll();

    public Article findByTitle(String title);

    public Article findById(int id);

    public Article findByArticleLink(String articleLink);
}
