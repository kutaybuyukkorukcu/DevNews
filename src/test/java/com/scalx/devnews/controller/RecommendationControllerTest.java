package com.scalx.devnews.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.service.LikeService;
import com.scalx.devnews.service.RecommendationService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ContentController.class)
@AutoConfigureMockMvc
public class RecommendationControllerTest {

    @Mock
    private RecommendationService recommendationService;

    @Mock
    private LikeService likeService;

    @InjectMocks
    private RecommendationController recommendationController;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FieldSetter fieldSetter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_recommend_whenAddLikedArticlesIntoLikeCollectionIsNotPresent() throws Exception {

        doThrow(new ResourceNotFoundException())
                .when(likeService).addLikedArticlesIntoLikeCollection();

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> {
                    likeService.addLikedArticlesIntoLikeCollection();
                });

        // assert response

        verify(likeService).addLikedArticlesIntoLikeCollection();
        verifyNoMoreInteractions(recommendationController);
    }

    @Test
    public void test_recommend_whenGetRecommendationsIsNotPresent() throws Exception {

        doThrow(new ResourceNotFoundException())
                .when(recommendationService).getRecommendations();

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> {
                    recommendationService.getRecommendations();
                });

        verify(likeService).addLikedArticlesIntoLikeCollection();
        verify(recommendationService).getRecommendations();
        verifyNoMoreInteractions(recommendationController);
    }

    @Test
    public void test_recommend_whenTopRecommendationsIntoArticleListIsPresent() throws Exception {
    }
}
