package com.scalx.devnews.service;

import com.scalx.devnews.entity.User;
import com.scalx.devnews.exception.UserAlreadyExistException;
import com.scalx.devnews.repository.RoleRepository;
import com.scalx.devnews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public void save(User user) {
        userRepository.saveAndFlush(user);
    }

    // TODO : Implement DTO's
    public User registerUser(final User userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) == null) {
            throw new UserAlreadyExistException("There is an account with that email address : " + userDto.getEmail());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        // TODO : Client sends encoded password to API. Using PasswordEncoder till Integration tests.
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.saveAndFlush(user);
    }

    public List<User> getUsers() {
        List<User> userList = userRepository.findAll();

        if (userList == null) {
            return Collections.emptyList();
        }

        return userList;
    }

    public Optional<User> findByUsername(String username) {
        User user = userRepository.findByUsername(username);

        return Optional.ofNullable(user);
    }

    public Optional<User> findById(int id) {
        User user = userRepository.findById(id);

        return Optional.ofNullable(user);
    }

    public Optional<User> findByEmail(String email) {
        User user = userRepository.findByEmail(email);

        return Optional.ofNullable(user);
    }


}
