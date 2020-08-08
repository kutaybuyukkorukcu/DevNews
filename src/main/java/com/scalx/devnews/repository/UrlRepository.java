package com.scalx.devnews.repository;

import com.scalx.devnews.entity.Like;
import com.scalx.devnews.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Integer> {

    public List<Url> findByIsNew();
}
