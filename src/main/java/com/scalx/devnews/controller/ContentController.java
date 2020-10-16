package com.scalx.devnews.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.dto.story.StoryRequest;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Comment;
import com.scalx.devnews.service.ArticleService;
import com.scalx.devnews.service.ContentService;
import com.scalx.devnews.service.UrlService;
import com.scalx.devnews.utils.StandardResponse;
import com.scalx.devnews.utils.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // /articles/{id}/
    @RequestMapping(value = "/content/articles", method = RequestMethod.POST)
    public ResponseEntity<?> getContentByType(@RequestParam String content,
                                              @RequestBody StoryRequest storyRequest) {

        List<Article> articleList = contentService.getArticles(storyRequest, content);

        JsonNode jsonNode = objectMapper.convertValue(articleList, JsonNode.class);

        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS.getMessage(),
                Date.valueOf(LocalDate.now()),
                jsonNode
        ));
    }

    // /articles/{id}/comments/{id}
    @RequestMapping(value = "/content/comments", method = RequestMethod.POST)
    public ResponseEntity<?> getContentCommentsByType(@RequestParam String content,
                                                      @RequestBody StoryRequest storyRequest) {

        List<Comment> commentList = contentService.getComments(storyRequest, content);

        JsonNode jsonNode = objectMapper.convertValue(commentList, JsonNode.class);

        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS.getMessage(),
                Date.valueOf(LocalDate.now()),
                jsonNode
        ));
    }
}
