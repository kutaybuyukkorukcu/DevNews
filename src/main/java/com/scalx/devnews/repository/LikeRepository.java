package com.scalx.devnews.repository;

import com.scalx.devnews.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    // method needs any param ? bool true/false
//    public List<Like> findByIsActive(boolean isActive);
}
