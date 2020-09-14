package com.scalx.devnews.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.dto.like.LikeRequest;
import com.scalx.devnews.entity.Like;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.repository.LikeRepository;
import com.scalx.devnews.service.LikeService;
import com.scalx.devnews.utils.ErrorResponse;
import com.scalx.devnews.utils.StandardResponse;
import com.scalx.devnews.utils.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FieldSetter<LikeRequest, Like> fieldSetter;

    @RequestMapping(value = "/likes", method = RequestMethod.GET)
    public ResponseEntity<?> getLikes() {

        List<Like> likeList = likeService.getLikesByActive();

        if (likeList.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse(
                    StatusResponse.NOT_FOUND.getStatusCode(),
                    StatusResponse.NOT_FOUND.getMessage(),
                    Date.valueOf(LocalDate.now())
            ));
        }

        JsonNode jsonNode = objectMapper.convertValue(likeList, JsonNode.class);

        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS.getMessage(),
                Date.valueOf(LocalDate.now()),
                jsonNode
        ));
    }

    @RequestMapping(value = "/likes", method = RequestMethod.POST)
    public ResponseEntity<?> postLike(@RequestBody LikeRequest likeRequest) {

        Like like = fieldSetter.setFieldsWhenCreate(likeRequest,
                modelMapper.map(likeRequest, Like.class));

        likeService.addLike(like);

        var response = new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS.getMessage(),
                Date.valueOf(LocalDate.now())
        );

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/likes/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getLikeByTitle(@PathVariable String title) {

        Optional<Like> like = likeService.getLikeByTitle(title);

        if (like.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse(
                    StatusResponse.NOT_FOUND.getStatusCode(),
                    StatusResponse.NOT_FOUND.getMessage(),
                    Date.valueOf(LocalDate.now())
            ));
        }

        JsonNode jsonNode = objectMapper.convertValue(like.get(), JsonNode.class);

        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS.getMessage(),
                Date.valueOf(LocalDate.now()),
                jsonNode
        ));
    }
}
