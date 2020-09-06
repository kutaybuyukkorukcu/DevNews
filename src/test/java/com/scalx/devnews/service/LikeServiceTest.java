package com.scalx.devnews.service;

import com.scalx.devnews.repository.LikeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class LikeServiceTest {

    @Mock
    LikeRepository likeRepository;

    @Mock
    UrlService urlService;

    @Mock
    CrawlerService crawlerService;

    @InjectMocks
    LikeService likeService;

    @Test
    public void test_getLikesByActive_whenFindAllByIsNewIsNotPresent() {

//        when(likeRepository.findAl)
    }
}
