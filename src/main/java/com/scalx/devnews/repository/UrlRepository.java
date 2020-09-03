package com.scalx.devnews.repository;

import com.scalx.devnews.entity.Like;
import com.scalx.devnews.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

//    public List<Url> findByIsActive(boolean isActive);
}
