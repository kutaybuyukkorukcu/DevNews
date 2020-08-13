package com.scalx.devnews.service;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public void save(Article article) {
        articleRepository.saveAndFlush(article);
    }

    public List<Article> getArticles() {
        List<Article> articleList = articleRepository.findAll();

        if (articleList == null) {
            return Collections.emptyList();
        }

        return articleList;
    }

    public boolean articleExist(Article article) {
        Article returnedArticle = articleRepository.findByTitle(article.getTitle());

        if (returnedArticle == null) {
            return false;
        }

        return true;
    }
}
