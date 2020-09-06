package com.scalx.devnews.controller;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.service.ArticleService;
import com.scalx.devnews.service.CrawlerService;
import com.scalx.devnews.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class CrawlerController {

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    private UrlService urlService;

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/crawl", method = RequestMethod.GET)
    public ResponseEntity<?> crawl() {

        List<String> articleLinkList = urlService.getArticleLinksAsList();

        if (articleLinkList.isEmpty()) {
            return ResponseEntity.ok(new Article());
        }

        for (String articleLink : articleLinkList) {
            Article article;

            try {
                article = crawlerService.crawlArticleLinkIntoArticle(articleLink);
            } catch (IOException e) {
                return ResponseEntity.ok(new Article());
            }

            articleService.addArticle(article);
        }

        List<Article> articleList = articleService.getArticles();

        if (articleList.isEmpty()) {
            return ResponseEntity.ok(new Article());
        }

        return ResponseEntity.ok(new Article());
    }
}
