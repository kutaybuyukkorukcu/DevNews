package com.scalx.devnews.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Like;
import com.scalx.devnews.entity.Recommendation;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void test_getTopRecommendationsFromList_whenSeveralRecommendationsArePresent() {

        List<Recommendation> expectedRecommendationList = recommendationService.getTopRecommendationsFromList(
                Arrays.asList(new Recommendation(3, 0.85),
                        new Recommendation(1, 0.35),
                        new Recommendation(2, 0.50)));

        assertThat(expectedRecommendationList.get(0).getArticleId()).isEqualTo(1);
        assertThat(expectedRecommendationList.get(0).getSimilarityScore()).isEqualTo(0.35);

        assertThat(expectedRecommendationList.get(0).getArticleId()).isEqualTo(2);
        assertThat(expectedRecommendationList.get(0).getSimilarityScore()).isEqualTo(0.50);

        assertThat(expectedRecommendationList.get(0).getArticleId()).isEqualTo(3);
        assertThat(expectedRecommendationList.get(0).getSimilarityScore()).isEqualTo(0.85);
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

        doThrow(new ResourceNotFoundException())
                .when(recommendationService).recommendationListToArticleList(recommendationList, recommendedArticles);

        verify(articleRepository).findById(articleId);
        verifyNoMoreInteractions(recommendationService);
    }

    // recommendationListToArticleList - recommendation dolu, recommendedArticles bos liste ata
    // findByArticleId() dolu donecek ve recommendedArticles uzerinde assert islemi gerceklestirilecek
    @Test
    public void test_recommendationListToArticleList_whenArticleIsPresent() {
        Recommendation recommendation = new Recommendation(1, 0.35);

        List<Recommendation> recommendationList = Arrays.asList(recommendation);

        int articleId = recommendationList.get(0).getArticleId();

        Article article = new Article("Whats new with Java 11", "Development", "Author",
                "Development|Java", "www.infoq.com/Whats-new-with-Java-11");

        List<Article> recommendedArticles = new ArrayList<>();

        when(articleRepository.findById(article.getId())).thenReturn(article);

        recommendationService.recommendationListToArticleList(recommendationList, recommendedArticles);

        assertThat(recommendedArticles).hasSize(1);
        assertThat(recommendedArticles.get(0)).isEqualTo(article);

        verify(articleRepository).findById(articleId);
        verifyNoMoreInteractions(recommendationService);
    }

    // getRecommendations() - likeService bos donsun
    @Test
    public void test_getRecommendations_whenGetLikesByActiveIsNotPresent() {
        List<Like> likeList = new ArrayList();

        when(likeService.getLikesByActive()).thenReturn(likeList);

        doThrow(new ResourceNotFoundException())
                .when(recommendationService).getRecommendations();

        verify(likeService).getLikesByActive();
        verifyNoMoreInteractions(recommendationService);
    }

    // getRecommendations() - likeService dolu donsun, recommendationService.getRecommendation'i mocklamaya calis
    // jsonObject full bos donsun
    @Test
    public void test_getRecommendations_whenGetRecommendationIsNotPresent() {
        List<Like> likeList = Arrays.asList(
                new Like("What's new with Java 11", "Development"),
                new Like("Comprehensive guide to unit testing", "Development")
        );

        when(likeService.getLikesByActive()).thenReturn(likeList);

        when(recommendationService.getRecommendation(likeList.get(0).getTitle())).thenReturn(null);

        doThrow(new ResourceNotFoundException())
                .when(recommendationService).getRecommendations();

        verify(likeService).getLikesByActive();
        verify(recommendationService).getRecommendation(likeList.get(0).getTitle());
        verifyNoMoreInteractions(recommendationService);
    }

    @Test
    public void test_getRecommendations_whenEverythingIsPresent() {
        List<Like> likeList = Arrays.asList(
                new Like("What's new with Java 11", "Development"),
                new Like("Comprehensive guide to unit testing", "Development")
        );

        when(likeService.getLikesByActive()).thenReturn(likeList);

        List<Recommendation> recommendationList = Arrays.asList(
                new Recommendation(395, 0.42356506617672646),
                new Recommendation(250, 0.2579225416660869),
                new Recommendation(468, 0.2302017341361332),
                new Recommendation(248, 0.2230720491097254),
                new Recommendation(490, 0.19212489538396202));

        List<Recommendation> initializedStaticList = new ArrayList<>();

        JsonNode jsonNode = objectMapper.convertValue(recommendationList, JsonNode.class);

        when(recommendationService.getRecommendation(likeList.get(0).getTitle())).thenReturn(jsonNode);

        JsonNode expectedJsonNode = recommendationService.getRecommendation(likeList.get(0).getTitle());

        assertThat(expectedJsonNode).isEqualTo(JsonNode.class);

        verify(likeService).getLikesByActive();
        verify(recommendationService.getRecommendation(likeList.get(0).getTitle()));

        verify(recommendationService).recommendationIntoRecommendationList(jsonNode, initializedStaticList);
        verifyNoMoreInteractions(recommendationService);
    }
}
