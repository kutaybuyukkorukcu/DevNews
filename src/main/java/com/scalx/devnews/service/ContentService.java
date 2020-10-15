package com.scalx.devnews.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.scalx.devnews.dto.content.ContentRequest;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Comment;
import com.scalx.devnews.entity.Like;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.helper.Validator;
import com.scalx.devnews.repository.ArticleRepository;
import com.scalx.devnews.utils.StandardResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// ContentService
@Transactional
@Service
public class ContentService {

    @Autowired
    private Validator validator;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    // Gets
    public List<Article> getArticles(ContentRequest request) {

        String resourceUrl = "http://localhost:8082/api/articles?content=hackernews ";

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(resourceUrl,
                request, JsonNode.class);

        if (response.getBody().get("statusCode").asText().equals("404")) {
            throw new ResourceNotFoundException();
        }

        List<Article> articleList = objectMapper.convertValue(
                response.getBody().get("data"),
                TypeFactory.defaultInstance().constructCollectionType(List.class, Article.class)
        );

        return articleList;
    }
//
//    public Optional<List<Comment>> getComments() {
//
//        String resourceUrl = "http://localhost:12072/api/comments";
//    }

    public Optional<Like> articleLinkToLike(String articleLink) {

        Article article = articleRepository.findByArticleLink(articleLink);

        if (article == null) {
            return Optional.empty();
        }

        Like like = new Like();

        like.setTitle(article.getTitle());
        like.setMainTopic(article.getMainTopic());

        return Optional.ofNullable(like);
    }
}
