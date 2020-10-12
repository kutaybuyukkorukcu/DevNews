package com.scalx.devnews;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.controller.ArticleController;
import com.scalx.devnews.dto.article.ArticleRequest;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.service.ArticleService;
import org.assertj.core.api.ObjectAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ArticleController.class)
@ActiveProfiles("test")
public class MockTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ArticleService articleService;

    @Test
    public void yo() throws Exception {

        String responseInString = "";

        try {
            when(articleService.getArticles()).thenReturn(Collections.emptyList());

            var response = mockMvc.perform(
                    get("/api/articles")
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse();

            responseInString = response.getContentAsString();
        } catch (NullPointerException e) {

            String error = e.getMessage();
            e.printStackTrace();
        }
//        var response = mockMvc.perform(
//                get("/api/articles")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();

//        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

    }
}
