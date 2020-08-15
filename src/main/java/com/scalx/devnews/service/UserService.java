package com.scalx.devnews.service;

import com.scalx.devnews.entity.User;
import com.scalx.devnews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.saveAndFlush(user);
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
}
