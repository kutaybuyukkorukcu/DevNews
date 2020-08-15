package com.scalx.devnews.controller;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.User;
import com.scalx.devnews.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
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

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);

        return ResponseEntity.ok(new Article());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(@PathVariable final int id) {

        Optional<User> user = userService.findById(id);

        if (!user.isPresent()) {
            return ResponseEntity.ok(new Article());
        }

        return ResponseEntity.ok(new Article());
    }
}
