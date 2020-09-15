package com.scalx.devnews.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.service.ArticleService;
import com.scalx.devnews.service.CrawlerService;
import com.scalx.devnews.service.UrlService;
import com.scalx.devnews.utils.ErrorResponse;
import com.scalx.devnews.utils.StandardResponse;
import com.scalx.devnews.utils.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            // article
            return ResponseEntity.ok(new ErrorResponse(
                    StatusResponse.NOT_FOUND.getStatusCode(),
                    StatusResponse.NOT_FOUND.getMessage(),
                    Date.valueOf(LocalDate.now())
            ));
        }

        // TODO : Move the function below from controller layer to service layer
        for (String articleLink : articleLinkList) {
            Article article = null;

            try {
                article = crawlerService.crawlArticleLinkIntoArticle(articleLink).get();
            } catch (IOException e) {
                e.printStackTrace();
//            throw new CrawlFailed or given article link is broken();
                // TODO : return null girilen article'in DB tarafindan atanan ID'yi maybe??

                // TODO : Mock response
                return ResponseEntity.ok(new ErrorResponse(
                        StatusResponse.NOT_FOUND.getStatusCode(),
                        StatusResponse.NOT_FOUND.getMessage(),
                        Date.valueOf(LocalDate.now())
                ));
            }

            articleService.addArticle(article);
        }

        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS.getMessage(),
                Date.valueOf(LocalDate.now())
        ));
    }
}
