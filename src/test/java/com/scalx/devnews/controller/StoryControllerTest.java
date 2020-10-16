package com.scalx.devnews.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.service.ArticleService;
import com.scalx.devnews.service.ContentService;
import com.scalx.devnews.service.UrlService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ContentController.class)
@AutoConfigureMockMvc
public class StoryControllerTest {

    @Mock
    private ContentService contentService;

    @Mock
    private UrlService urlService;

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ContentController contentController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void test_crawl_whenGetArticleLinksAsListIsNotPresent() throws Exception {

        when(urlService.getArticleLinksAsList()).thenReturn(Collections.emptyList());

        var response = mockMvc.perform(
                get("/api/crawl")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        verify(urlService).getArticleLinksAsList();
        verifyNoMoreInteractions(contentController);
    }

//    @Test
//    public void test_crawl_whenCrawlArticleLinkIntoArticleIsNotPresent() throws Exception {
//
//        when(urlService.getArticleLinksAsList()).thenReturn(Arrays.asList(
//                "www.dzone.com/Comprehensive-guide-to-unit-testing"
//                )
//        );
//
//        when(contentService.crawlArticleLinkIntoArticle("www.dzone.com/Comprehensive-guide-to-unit-testing"))
//                .thenThrow(new IOException());
//
//        var response = mockMvc.perform(
//                get("/api/crawl")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        String responseInString = response.getContentAsString();
//
//        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);
//
//        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
//        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
//        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());
//
//        assertThatExceptionOfType(IOException.class)
//                .isThrownBy(() -> {
//                    contentService.crawlArticleLinkIntoArticle("www.dzone.com/Comprehensive-guide-to-unit-testing");
//                });
//
//        verify(urlService).getArticleLinksAsList();
//        verify(contentService).crawlArticleLinkIntoArticle("www.dzone.com/Comprehensive-guide-to-unit-testing");
//        verifyNoMoreInteractions(contentController);
//    }
//
//    @Test
//    public void test_crawl_whenEverythingIsPresent() throws Exception {
//
//        when(urlService.getArticleLinksAsList()).thenReturn(Arrays.asList(
//                "www.dzone.com/Comprehensive-guide-to-unit-testing"
//                )
//        );
//
//        Article article = new Article("Comprehensive Guide to Unit Testing",
//                "Development", "Author", "Development|Java",
//                "www.dzone.com/Comprehensive-guide-to-unit-testing");
//
//
//        when(contentService.crawlArticleLinkIntoArticle("www.dzone.com/Comprehensive-guide-to-unit-testing").get())
//                .thenReturn(article);
//
//        var response = mockMvc.perform(
//                get("/api/crawl")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        String responseInString = response.getContentAsString();
//
//        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);
//
//        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
//        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
//        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());
//
//        verify(urlService).getArticleLinksAsList();
//        verify(contentService).crawlArticleLinkIntoArticle("www.dzone.com/Comprehensive-guide-to-unit-testing");
//        verifyNoMoreInteractions(contentController);
//    }
}
