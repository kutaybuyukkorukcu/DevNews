package com.scalx.devnews.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.dto.BaseRequest;
import com.scalx.devnews.dto.like.LikeRequest;
import com.scalx.devnews.entity.Like;
import com.scalx.devnews.exception.GlobalControllerAdvisor;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.service.LikeService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LikeController.class)
@AutoConfigureMockMvc
public class LikeControllerTest {

    @Mock
    LikeService likeService;

    @InjectMocks
    LikeController likeController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FieldSetter<LikeRequest, Like> fieldSetter;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void test_getLikes_whenGetLikesByActiveIsNotPresent() throws Exception {
        when(likeService.getLikesByActive()).thenReturn(Collections.emptyList());

        var response = mockMvc.perform(
                get("/api/likes")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        verify(likeService).getLikesByActive();
        verifyNoMoreInteractions(likeController);
    }

    @Test
    public void test_getLikes_whenGetLikesByActiveIsPresent() throws Exception {

        LikeRequest likeRequest = new LikeRequest("Scalx", "What's new with Java 11",
                "Development");
        LikeRequest likeRequest1 = new LikeRequest("Scalx", "Comprehensive guide to unit testing",
                "Development");

        Like like = fieldSetter.setFieldsWhenCreateWithGivenId(likeRequest,
                modelMapper.map(likeRequest, Like.class), 1);

        Like like1 = fieldSetter.setFieldsWhenCreateWithGivenId(likeRequest1,
                modelMapper.map(likeRequest1, Like.class), 2);

        when(likeService.getLikesByActive()).thenReturn(
                Arrays.asList(like, like1)
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

        String data = objectMapper.writeValueAsString(Arrays.asList(like, like1));

        assertThat(jsonNode.get("data").asText()).isEqualTo(data);

        verify(likeService).getLikesByActive();
        verifyNoMoreInteractions(likeController);
    }

    @Test
    public void test_postLike_whenEntityIsNotPresent() throws Exception {


    }

    @Test
    public void test_postLike_whenEntityIsPresent() throws Exception {

        LikeRequest likeRequest = new LikeRequest("Scalx", "What's new with Java 11",
                "Development");

        var response = mockMvc.perform(post("/api/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(likeRequest)))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(200);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Success - Mock for now!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        Like like = fieldSetter.setFieldsWhenCreateWithGivenId(likeRequest,
                modelMapper.map(likeRequest, Like.class), 1);

        verify(likeService).addLike(like);
        verifyNoMoreInteractions(likeController);
    }

    @Test
    public void test_getLikeByTitle_whenGetLikeByTitleIsNotPresent() throws Exception {

        String title = "Non-existed article title";

        when(likeService.getLikeByTitle(title)).thenReturn(Optional.empty());

        var response = mockMvc.perform(get("/api/likes/{title}", title))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        verify(likeService).getLikesByActive();
        verifyNoMoreInteractions(likeController);

    }

    @Test
    public void test_getLikeByTitle_whenGetLikeByTitleIsPresent() throws Exception {

        LikeRequest likeRequest = new LikeRequest("Scalx", "What's new with Java 11",
                "Development");

        Like like = fieldSetter.setFieldsWhenCreateWithGivenId(likeRequest,
                modelMapper.map(likeRequest, Like.class), 1);

        when(likeService.getLikeByTitle(likeRequest.getTitle()).get()).thenReturn(like);

        var response = mockMvc.perform(get("/api/likes/{title}", likeRequest.getTitle()))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.convertValue(like, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(200);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Success - Mock for now!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        String data = objectMapper.writeValueAsString(Arrays.asList(like));

        assertThat(jsonNode.get("data").asText()).isEqualTo(data);

        verify(likeService).getLikeByTitle(likeRequest.getTitle());
        verifyNoMoreInteractions(likeController);
    }
}
