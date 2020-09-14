package com.scalx.devnews.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.dto.like.LikeRequest;
import com.scalx.devnews.dto.url.UrlRequest;
import com.scalx.devnews.entity.Like;
import com.scalx.devnews.entity.Url;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.service.UrlService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UrlController.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UrlControllerTest {

    @Mock
    UrlService urlService;

    @InjectMocks
    UrlController urlController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FieldSetter<UrlRequest, Url> fieldSetter;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void test_getArticleLinks_whenGetArticleLinksAsListIsNotPresent() throws Exception {

        when(urlService.getArticleLinksAsList()).thenReturn(Collections.emptyList());

        var response = mockMvc.perform(
                get("/api/urls")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        verify(urlService).getArticleLinksAsList();
        verifyNoMoreInteractions(urlController);
    }

    @Test
    public void test_getArticleLinks_whenGetArticleLinksAsListIsPresent() throws Exception {

        when(urlService.getArticleLinksAsList()).thenReturn(Arrays.asList(
                "www.infoq.com/Whats-new-with-Java-11",
                "www.dzone.com/Comprehensive-guide-to-unit-testing"
                )
        );

        var response = mockMvc.perform(
                get("/api/urls")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(200);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Success - Mock for now!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        String data = objectMapper.writeValueAsString(Arrays.asList(
                "www.infoq.com/Whats-new-with-Java-11",
                "www.dzone.com/Comprehensive-guide-to-unit-testing"
                )
        );

        assertThat(jsonNode.get("data").asText()).isEqualTo(data);

        verify(urlService).getArticleLinksAsList();
        verifyNoMoreInteractions(urlController);
    }

    @Test
    public void test_getUrls_whenGetUrlsByActiveIsNotPresent() throws Exception {

        when(urlService.getUrls()).thenReturn(Collections.emptyList());

        var response = mockMvc.perform(
                get("/api/urls")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        verify(urlService).getArticleLinksAsList();
        verifyNoMoreInteractions(urlController);
    }

    @Test
    public void test_getUrls_whenGetUrlsByActiveIsPresent() throws Exception {

        UrlRequest urlRequest = new UrlRequest("Scalx",
                "www.infoq.com/Whats-new-with-Java-11");

        UrlRequest urlRequest1 = new UrlRequest("Scalx",
                "www.dzone.com/Comprehensive-guide-to-unit-testing");

        Url url = fieldSetter.setFieldsWhenCreateWithGivenId(urlRequest,
                modelMapper.map(urlRequest, Url.class), 1);

        Url url1 = fieldSetter.setFieldsWhenCreateWithGivenId(urlRequest1,
                modelMapper.map(urlRequest, Url.class), 2);


        when(urlService.getUrls()).thenReturn(
                Arrays.asList(url, url1)
        );

        var response = mockMvc.perform(
                get("/api/urls")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        String data = objectMapper.writeValueAsString(Arrays.asList(url, url1));

        assertThat(jsonNode.get("data").asText()).isEqualTo(data);

        verify(urlService).getUrlsByActive();
        verifyNoMoreInteractions(urlController);
    }

    @Test
    public void test_postUrl_whenEntityIsNotPresent() throws Exception {

    }

    @Test
    public void test_postUrl_whenEntityIsPresent() throws Exception {

        UrlRequest urlRequest = new UrlRequest("Scalx",
                "www.infoq.com/Whats-new-with-Java-11");

        var response = mockMvc.perform(post("api/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(urlRequest)))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(200);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Success - Mock for now!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        Url url = fieldSetter.setFieldsWhenCreateWithGivenId(urlRequest,
                modelMapper.map(urlRequest, Url.class), 1);

        verify(urlService).addUrl(url);
        verifyNoMoreInteractions(urlController);
    }
}
