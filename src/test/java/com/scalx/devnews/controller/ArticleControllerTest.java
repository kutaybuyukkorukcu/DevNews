package com.scalx.devnews.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.dto.article.ArticleRequest;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Like;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.helper.JasyptPropertyService;
import com.scalx.devnews.repository.ArticleRepository;
import com.scalx.devnews.service.ArticleService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.plugins.MockMaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ArticleController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = ArticleController.class)
public class ArticleControllerTest {

    @Mock
    ArticleService articleService;

//    @InjectMocks
//    ArticleController articleController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FieldSetter<ArticleRequest, Article> fieldSetter;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void test_getArticles_whenGetArticlesIsNotPresent() throws Exception {

        when(articleService.getArticles()).thenReturn(Collections.emptyList());

        var response = mockMvc.perform(
                get("/api/articles")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

//        verify(articleService).getArticles();
//        verifyNoMoreInteractions(articleController);
    }

    @Test
    public void test_getArticles_whenGetArticlesIsPresent() throws Exception {

        ArticleRequest articleRequest = new ArticleRequest("Scalx", "What's new with Java 11",
                "Development", "KutayDev", "Development|Java",
                "www.infoq.com/Whats-new-with-Java-11");

        ArticleRequest articleRequest1 = new ArticleRequest("Scalx", "Comprehensive Guide to Unit Testing",
                "Development", "Author", "Development|Java",
                "www.dzone.com/Comprehensive-guide-to-unit-testing");

        Article article = fieldSetter.setFieldsWhenCreateWithGivenId(articleRequest,
                modelMapper.map(articleRequest, Article.class), 1);

        Article article1 = fieldSetter.setFieldsWhenCreateWithGivenId(articleRequest,
                modelMapper.map(articleRequest1, Article.class), 2);


        when(articleService.getArticles()).thenReturn(
                Arrays.asList(article, article1)
        );

        var response = mockMvc.perform(
                get("/api/likes")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(200);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Success - Mock for now!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        String data = objectMapper.writeValueAsString(Arrays.asList(article, article1));

        assertThat(jsonNode.get("data").asText()).isEqualTo(data);

//        verify(articleService).getArticles();
//        verifyNoMoreInteractions(articleController);
    }

//    @Test
//    public void test_postArticle_whenEntityIsNotPresent() throws Exception {
//
//    }

    @Test
    public void test_postArticle_whenEntityIsPresent() throws Exception {

        ArticleRequest articleRequest = new ArticleRequest("Scalx", "What's new with Java 11",
                "Development", "KutayDev", "Development|Java",
                "www.infoq.com/Whats-new-with-Java-11");

        var response = mockMvc.perform(post("/api/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleRequest)))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(200);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Success - Mock for now!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        Article article = fieldSetter.setFieldsWhenCreateWithGivenId(articleRequest,
                modelMapper.map(articleRequest, Article.class), 1);

//        verify(articleService).addArticle(article);
//        verifyNoMoreInteractions(articleController);
    }
}
