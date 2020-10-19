package com.scalx.devnews.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.scalx.devnews.dto.comment.CommentRequest;
import com.scalx.devnews.dto.comment.CommentResponse;
import com.scalx.devnews.dto.story.StoryRequest;
import com.scalx.devnews.dto.story.StoryResponse;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Comment;
import com.scalx.devnews.entity.Like;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.helper.Validator;
import com.scalx.devnews.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    public List<StoryResponse> getStories(StoryRequest request, String content) {

        String resourceUrl = "http://localhost:8082/api/articles?content=" + content;

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(resourceUrl,
                request, JsonNode.class);

        if (response.getBody().get("statusCode").asText().equals("404")) {
            throw new ResourceNotFoundException();
        }

        List<StoryResponse> articleList = objectMapper.convertValue(
                response.getBody().get("data"),
                TypeFactory.defaultInstance().constructCollectionType(List.class, StoryResponse.class)
        );

        return articleList;
    }

    public List<CommentResponse> getComments(CommentRequest request, String content) {

        String resourceUrl = "http://localhost:8082/api/comments?content=" + content;

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(resourceUrl,
                request, JsonNode.class);

        if (response.getBody().get("statusCode").asText().equals("404")) {
            throw new ResourceNotFoundException();
        }

        List<CommentResponse> commentList = objectMapper.convertValue(
                response.getBody().get("data"),
                TypeFactory.defaultInstance().constructCollectionType(List.class, CommentResponse.class)
        );

        return commentList;
    }

//    public List<>
}
