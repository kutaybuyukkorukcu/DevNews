package com.scalx.devnews.repository;

import com.scalx.devnews.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

    public User findById(int id);
}
