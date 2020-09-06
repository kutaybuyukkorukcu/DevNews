package com.scalx.devnews.controller;

import com.scalx.devnews.entity.Like;
import com.scalx.devnews.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(value = "/likes", method = RequestMethod.GET)
    public ResponseEntity<?> getLikes() {
        // get active likes

        return ResponseEntity.ok(new Like());
    }

    @RequestMapping(value = "/likes", method = RequestMethod.POST)
    public ResponseEntity<?> postLike() {

        return ResponseEntity.ok(new Like());
    }

    @RequestMapping(value = "/likes/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getLikeByTitle(@PathVariable String title) {

        return ResponseEntity.ok(new Like());
    }
}
