package com.scalx.devnews.controller;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.exception.RecommendationHttpException;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.service.LikeService;
import com.scalx.devnews.service.RecommendationService;
import com.scalx.devnews.utils.CacheLists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class RecommendationController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private RecommendationService recommendationService;

    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public ResponseEntity<?> recommend() {

        CacheLists.recommendedArticles.clear();

        // TODO : based on user ID, update user's old liked articles isNew value from 1 to 0
        try {
            likeService.addLikedArticlesIntoLikeCollection();
            recommendationService.getRecommendations();
//            recommendationService.topRecommendationsIntoArticleList();

        } catch (RecommendationHttpException | ResourceNotFoundException e) {

            return ResponseEntity.ok(new Article());
        }

        return ResponseEntity.ok(new Article());
    };
}

