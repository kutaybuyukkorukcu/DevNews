package com.scalx.devnews.controller;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> articles() {

        List<Article> articleList = articleService.getArticles();

        if (articleList.isEmpty()) {
            return ResponseEntity.ok(new Article());
        }

        return ResponseEntity.ok(new Article());
    }
}
