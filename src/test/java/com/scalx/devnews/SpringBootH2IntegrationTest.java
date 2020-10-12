package com.scalx.devnews;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.configuration.PersistenceJPAConfig;
import com.scalx.devnews.dto.article.ArticleRequest;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.utils.StandardResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {PersistenceJPAConfig.class, DevnewsApplicationTests.class})
@ActiveProfiles("integration")
public class SpringBootH2IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String CONTENT_TYPE = "application/json";

    private static final String port = "8080";

    private static final String baseUrl = "http://localhost:";

    private static final String URI = "/api/users/1";

    private ObjectMapper objectMapper = new ObjectMapper();

    private FieldSetter<ArticleRequest, Article> fieldSetter = new FieldSetter<>();

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void yo() throws Exception {

        ResponseEntity<StandardResponse> response =
                this.restTemplate.getForEntity(baseUrl + port + URI, StandardResponse.class);

        StandardResponse resp = response.getBody();



//        String responseInString = response.getBody();
//
//        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);
//
//        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
//        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
//        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());
    }
}
