package com.scalx.devnews.controller;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.User;
import com.scalx.devnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
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

    @RequestMapping(value = "/v1/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signup() {

//        User user = new Gson().fromJson(request.body(), User.class);

//        userService.save(user);

        return ResponseEntity.ok(new Article());
    }

    @RequestMapping(value = "/v1/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(@PathVariable final int id) {

        Optional<User> user = userService.findById(id);

        if (!user.isPresent()) {
            return ResponseEntity.ok(new Article());
        }

        return ResponseEntity.ok(new Article());
    }
}
