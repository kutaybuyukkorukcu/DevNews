package com.scalx.devnews.service;

import com.scalx.devnews.entity.Like;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service
public class LikeService {

    @Autowired
    private UrlService urlService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private LikeRepository likeRepository;

    public void addLike(Like like) {
        likeRepository.save(like);
    }

    public List<Like> getLikes() {

        List<Like> likeList = likeRepository.findAll();

        return likeList != null ? likeList
                : Collections.emptyList();
    }

    public List<Like> getLikesByActive() {

        List<Like> likeList = likeRepository.findAllByActive();

        return likeList != null ? likeList
                : Collections.emptyList();
    }

    public void addLikedArticlesIntoLikeCollection() {
        List<String> articleLinkList = urlService.getArticleLinksAsList();

        if (articleLinkList.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        for (String articleLink : articleLinkList) {
            Optional<Like> like = articleService.articleLinkToLike(articleLink);

            if (like.isEmpty()) {

                // TODO : error/log handling
                // TODO : throw custom exception

                throw new ResourceNotFoundException();
            }

            likeRepository.save(like.get());
        }
    }

    public Optional<Like> getLikeByTitle(String title) {
        Like like = likeRepository.findByTitle(title);

        return like != null ? Optional.of(like)
                : Optional.empty();
    }
}
