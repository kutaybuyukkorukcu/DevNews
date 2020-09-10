package com.scalx.devnews.service;

import com.scalx.devnews.entity.Recommendation;
import com.scalx.devnews.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RecommendationServiceTest {

    @Mock
    LikeService likeService;

    @Mock
    ArticleService articleService;

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    RecommendationService recommendationService;

    @Test
    public void test_getTopRecommendationsFromList_whenSeveralRecommendationsArePresent() {

    }
}
