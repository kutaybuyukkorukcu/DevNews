package com.scalx.devnews.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.exception.RecommendationHttpException;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.service.LikeService;
import com.scalx.devnews.service.RecommendationService;
import com.scalx.devnews.helper.CacheLists;
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

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class RecommendationController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FieldSetter fieldSetter;

    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public ResponseEntity<?> recommend() {

        CacheLists.recommendedArticles.clear();

        // TODO : based on user ID, update user's old liked articles isNew value from 1 to 0

        likeService.addLikedArticlesIntoLikeCollection();
        recommendationService.getRecommendations();
        recommendationService.topRecommendationsIntoArticleList();

        JsonNode jsonNode = objectMapper.convertValue(CacheLists.recommendedArticles, JsonNode.class);

        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS.getMessage(),
                Date.valueOf(LocalDate.now()),
                jsonNode
        ));
    };
}

