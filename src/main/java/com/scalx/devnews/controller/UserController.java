package com.scalx.devnews.controller;

import com.scalx.devnews.dto.user.UserRequest;
import com.scalx.devnews.dto.user.UserResponse;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.User;
import com.scalx.devnews.exception.InvalidJwtAuthenticationException;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.security.JwtUtil;
import com.scalx.devnews.security.UserDetailsServiceImpl;
import com.scalx.devnews.service.UserService;
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

        User _user = modelMapper.map(userRequest, User.class);

        // TODO : Remove it after developing client
        _user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
//        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));

//        User user = fieldSetter.setFieldsWhenCreate(userRequest, _user);

        userService.save(_user);

        return ResponseEntity.ok(_user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(@PathVariable int id) {

        Optional<User> user = userService.findById(id);

        if (!user.isPresent()) {
            return ResponseEntity.ok(new Article());
        }

        return ResponseEntity.ok(new Article());
    }
}
