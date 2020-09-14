package com.scalx.devnews.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.dto.user.UserRequest;
import com.scalx.devnews.dto.user.UserResponse;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.User;
import com.scalx.devnews.exception.InvalidJwtAuthenticationException;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.security.JwtUtil;
import com.scalx.devnews.security.UserDetailsServiceImpl;
import com.scalx.devnews.service.UserService;
import com.scalx.devnews.utils.ErrorResponse;
import com.scalx.devnews.utils.StandardResponse;
import com.scalx.devnews.utils.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FieldSetter<UserRequest, User> fieldSetter;

    @RequestMapping(value = "/users/sign-in", method = RequestMethod.POST)
    public ResponseEntity<?> signin(@RequestBody UserRequest userRequest) throws InvalidJwtAuthenticationException {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidJwtAuthenticationException("Incorrect username or password", e);
        }

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(userRequest.getUsername());

        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new UserResponse(jwt));
    }

    @RequestMapping(value = "/users/sign-up", method = RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody UserRequest userRequest) {

        // TODO : Client sends encoded password to API. Using PasswordEncoder till Integration tests.
        // TODO : Remove it after developing client

        User user = fieldSetter.setFieldsWhenCreate(userRequest,
                modelMapper.map(userRequest, User.class));

        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
//        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));

        userService.addUser(user);

        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(@PathVariable int id) {

        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse(
                    StatusResponse.NOT_FOUND.getStatusCode(),
                    StatusResponse.NOT_FOUND.getMessage(),
                    Date.valueOf(LocalDate.now())
            ));
        }

        JsonNode jsonNode = objectMapper.convertValue(user.get(), JsonNode.class);

        return ResponseEntity.ok(new StandardResponse(
                StatusResponse.SUCCESS.getStatusCode(),
                StatusResponse.SUCCESS.getMessage(),
                Date.valueOf(LocalDate.now()),
                jsonNode
        ));
    }
}
