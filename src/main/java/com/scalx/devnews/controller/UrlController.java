package com.scalx.devnews.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.dto.url.UrlRequest;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Url;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.service.CrawlerService;
import com.scalx.devnews.service.UrlService;
import com.scalx.devnews.utils.ErrorResponse;
import com.scalx.devnews.utils.StandardResponse;
import com.scalx.devnews.utils.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FieldSetter<UrlRequest, Url> fieldSetter;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value = "/links", method = RequestMethod.GET)
    public ResponseEntity<?> getArticleLinks() {

        List<String> articleLinkList = urlService.getArticleLinksAsList();

        if (articleLinkList.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse(
                    StatusResponse.NOT_FOUND.getStatusCode(),
                    StatusResponse.NOT_FOUND,
                    StatusResponse.NOT_FOUND.getMessage(),
                    LocalDateTime.now()
            ));
        }

        JsonNode jsonNode = objectMapper.convertValue(articleLinkList, JsonNode.class);

        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS,
                StatusResponse.SUCCESS.getMessage(),
                LocalDateTime.now(),
                jsonNode
        ));
    }

    @RequestMapping(value = "/urls", method = RequestMethod.GET)
    public ResponseEntity<?> getUrls() {

        List<Url> urlList = urlService.getUrlsByActive();

        if (urlList.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse(
                    StatusResponse.NOT_FOUND.getStatusCode(),
                    StatusResponse.NOT_FOUND,
                    StatusResponse.NOT_FOUND.getMessage(),
                    LocalDateTime.now()
            ));
        }

        JsonNode jsonNode = objectMapper.convertValue(urlList, JsonNode.class);

        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS,
                StatusResponse.SUCCESS.getMessage(),
                LocalDateTime.now(),
                jsonNode
        ));
    }

    @RequestMapping(value = "/urls", method = RequestMethod.POST)
    public ResponseEntity<?> postUrl(@RequestBody UrlRequest urlRequest) {

        Url _url = modelMapper.map(urlRequest, Url.class);

        Url url = fieldSetter.setFieldsWhenCreate(urlRequest, _url);

        urlService.addUrl(url);

        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS,
                StatusResponse.SUCCESS.getMessage(),
                LocalDateTime.now()
        ));
    }
}
