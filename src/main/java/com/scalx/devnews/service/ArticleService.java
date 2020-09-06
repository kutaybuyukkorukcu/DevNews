package com.scalx.devnews.service;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public void addArticle(Article article) {
        articleRepository.save(article);
    }

    public List<Article> getArticles() {

        List<Article> articleList = articleRepository.findAll();

        // TODO : Check if the null check works otherwise use != null.
        if (articleList == null) {
            return Collections.emptyList();
        }

        return articleList;
    }

    public boolean articleExist(Article article) {
        Article _article = articleRepository.findByTitle(article.getTitle());

        if (_article == null) {
            return false;
        }

        return true;
    }
}
