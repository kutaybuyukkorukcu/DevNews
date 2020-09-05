package com.scalx.devnews.controller;

import com.scalx.devnews.dto.UserRequest;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.User;
import com.scalx.devnews.service.UserService;
import com.scalx.devnews.utils.ErrorResponse;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(value = "/users/sign-in", method = RequestMethod.POST)
    public ResponseEntity<?> signin() {

//        User requestUser = new Gson().fromJson(request.body(), User.class);

        List<String> roles = new ArrayList<>();
        roles.add("admin");

//        Optional<User> user = userService.findByUsername(requestUser.getUsername());
//        if (!user.isPresent()) {
//            return ResponseEntity.ok(new Article());
//        }

//        String token = jwtAuthentication.createToken(requestUser.getUsername(), roles);

        return ResponseEntity.ok(new Article());
    }

    @RequestMapping(value = "/users/sign-up", method = RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody UserRequest userRequest) {
        // TODO : Client sends encoded password to API. Using PasswordEncoder till Integration tests.

        User user = modelMapper.map(userRequest, User.class);


        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setCreatedBy(userRequest.getUsername());
        user.setCreatedDate(java.sql.Date.valueOf(LocalDate.now()));
        user.setActive(true);
        user.setLastModifiedBy(userRequest.getUsername());
        user.setLastModifiedDate(java.sql.Date.valueOf(LocalDate.now()));

        userService.save(user);

        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(@PathVariable final int id) {

        Optional<User> user = userService.findById(id);

        if (!user.isPresent()) {
            return ResponseEntity.ok(new Article());
        }

        return ResponseEntity.ok(new Article());
    }
}
