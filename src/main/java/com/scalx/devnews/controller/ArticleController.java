package com.scalx.devnews.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.dto.article.ArticleRequest;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.BaseEntity;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.service.ArticleService;
import com.scalx.devnews.utils.ErrorResponse;
import com.scalx.devnews.utils.StandardResponse;
import com.scalx.devnews.utils.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FieldSetter fieldSetter;

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public ResponseEntity<?> getArticles() {

        List<Article> articleList = articleService.getArticles();

        if (articleList.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse(
               StatusResponse.NOT_FOUND.getStatusCode(),
               StatusResponse.NOT_FOUND,
               StatusResponse.NOT_FOUND.getMessage(),
                    LocalDateTime.now()
            ));
        }

        JsonNode jsonNode = modelMapper.map(articleList, JsonNode.class);


        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS,
                StatusResponse.SUCCESS.getMessage(),
                LocalDateTime.now(),
                jsonNode
        ));
    }

    @RequestMapping(value = "/articles", method = RequestMethod.POST)
    public ResponseEntity<?> postArticle(@RequestBody ArticleRequest articleRequest) throws JsonProcessingException {

        Article _article = modelMapper.map(articleRequest, Article.class);

        Article article = (Article) fieldSetter.setFieldsWhenCreate(articleRequest, _article);

        articleService.addArticle(article);

        JsonNode jsonNode = objectMapper.readValue(
                objectMapper.writeValueAsString(_article),
                JsonNode.class
        );

        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS,
                StatusResponse.SUCCESS.getMessage(),
                LocalDateTime.now(),
                jsonNode
        ));
    }
}
