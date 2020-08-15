package com.scalx.devnews.repository;

import com.scalx.devnews.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // method needs any param ? bool true/false
    public List<Like> findByIsNew();
}
