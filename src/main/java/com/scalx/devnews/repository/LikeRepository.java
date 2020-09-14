package com.scalx.devnews.repository;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

    @Query(value = "SELECT * FROM likes", nativeQuery = true)
    public List<Like> findAll();

    @Query(value = "SELECT * FROM likes WHERE is_active = true", nativeQuery = true)
    public List<Like> findAllByActive();

    public Like findByTitle(String title);
}
