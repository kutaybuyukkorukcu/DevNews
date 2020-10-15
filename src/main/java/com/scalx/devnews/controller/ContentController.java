package com.scalx.devnews.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.dto.content.ContentRequest;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.service.ArticleService;
import com.scalx.devnews.service.ContentService;
import com.scalx.devnews.service.UrlService;
import com.scalx.devnews.utils.ErrorResponse;
import com.scalx.devnews.utils.StandardResponse;
import com.scalx.devnews.utils.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private UrlService urlService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value = "/content", method = RequestMethod.GET)
    public ResponseEntity<?> getContentByType() {

        ContentRequest request = new ContentRequest("", "top");

        List<Article> articleList = contentService.getArticles(request);

        JsonNode jsonNode = objectMapper.convertValue(articleList, JsonNode.class);

        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS.getMessage(),
                Date.valueOf(LocalDate.now()),
                jsonNode
        ));
    }
}
