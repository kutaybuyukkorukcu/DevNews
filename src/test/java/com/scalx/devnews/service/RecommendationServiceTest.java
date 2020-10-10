package com.scalx.devnews.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Like;
import com.scalx.devnews.entity.Recommendation;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.repository.ArticleRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RecommendationServiceTest {

    @Mock
    LikeService likeService;

    @Mock
    ArticleRepository articleRepository;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    RecommendationService recommendationService;

    @Test
    public void test_getTopRecommendationsFromList_whenSeveralRecommendationsArePresent() {

        List<Recommendation> expectedRecommendationList = recommendationService.getTopRecommendationsFromList(
                Arrays.asList(new Recommendation(3, 0.85),
                        new Recommendation(1, 0.35),
                        new Recommendation(2, 0.50)));

        assertThat(expectedRecommendationList.get(0).getArticleId()).isEqualTo(3);
        assertThat(expectedRecommendationList.get(0).getSimilarityScore()).isEqualTo(0.85);

        assertThat(expectedRecommendationList.get(1).getArticleId()).isEqualTo(2);
        assertThat(expectedRecommendationList.get(1).getSimilarityScore()).isEqualTo(0.50);

        assertThat(expectedRecommendationList.get(2).getArticleId()).isEqualTo(1);
        assertThat(expectedRecommendationList.get(2).getSimilarityScore()).isEqualTo(0.35);
    }

    // recommendationListToArticleList - recommendations dolu, recommendArticles bos liste ata
    // findByArticleId() null donecek ve ResourceNoutFoundException() beklenecek
    @Test
    public void test_recommendationListToArticleList_whenArticleIsNotPresent() {
        Recommendation recommendation = new Recommendation(1, 0.35);

        List<Recommendation> recommendationList = Arrays.asList(recommendation);

        int articleId = recommendationList.get(0).getArticleId();

        List<Article> recommendedArticles = new ArrayList<>();

        when(articleRepository.findById(articleId)).thenReturn(null);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> {
                    recommendationService.recommendationListToArticleList(recommendationList, recommendedArticles);
                });

        verify(articleRepository).findById(articleId);
    }

    // recommendationListToArticleList - recommendation dolu, recommendedArticles bos liste ata
    // findByArticleId() dolu donecek ve recommendedArticles uzerinde assert islemi gerceklestirilecek
    @Test
    public void test_recommendationListToArticleList_whenArticleIsPresent() {
        Recommendation recommendation = new Recommendation(1, 0.35);

        List<Recommendation> recommendationList = Collections.singletonList(recommendation);

        int articleId = recommendationList.get(0).getArticleId();

        Article article = new Article("Whats new with Java 11", "Development", "Author",
                "Development|Java", "www.infoq.com/Whats-new-with-Java-11");

        List<Article> recommendedArticles = new ArrayList<>();

        when(articleRepository.findById(articleId)).thenReturn(article);

        recommendationService.recommendationListToArticleList(recommendationList, recommendedArticles);

        assertThat(recommendedArticles).hasSize(1);
        assertThat(recommendedArticles.get(0)).isEqualTo(article);

        verify(articleRepository).findById(articleId);
    }

    // getRecommendations() - likeService bos donsun
    @Test
    public void test_getRecommendations_whenGetLikesByActiveIsNotPresent() {

        when(likeService.getLikesByActive()).thenReturn(Collections.emptyList());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> {
                    recommendationService.getRecommendations();
                });

        verify(likeService).getLikesByActive();
    }

    // getRecommendations() - likeService dolu donsun, recommendationService.getRecommendation'i mocklamaya calis
    // jsonObject full bos donsun
    @Test
    public void test_getRecommendations_whenGetRecommendationIsNotPresent() {

        List<Like> likeList = Arrays.asList(
                new Like("What's new with Java 11", "Development"),
                new Like("Comprehensive guide to unit testing", "Development")
        );

        String resourceUrl = "http://localhost:5000/api/recommend";

        when(likeService.getLikesByActive()).thenReturn(likeList);

        when(restTemplate.getForEntity(resourceUrl, JsonNode.class)).thenReturn(ResponseEntity.ok(null));

//        when(recommendationService.getRecommendation(likeList.get(0).getTitle())).thenReturn(null);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> {
                    recommendationService.getRecommendations();
                });

        verify(likeService).getLikesByActive();
    }

//    @Test
//    public void test_getRecommendations_whenEveryMethodIsPresent() {
//
//    }
}
