package com.scalx.devnews.service;

import com.scalx.devnews.entity.Like;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private UrlService urlService;

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    private LikeRepository likeRepository;

    public List<Like> getLikes() {
        List<Like> likeList = likeRepository.findAll();

        if (likeList == null) {
            return Collections.emptyList();
        }

        return likeList;
    }

    public List<Like> getNewLikes() {
        List<Like> likeList = likeRepository.findByIsNew();

        if (likeList == null) {
            return Collections.emptyList();
        }

        return likeList;
    }

    public void addLikedArticlesIntoLikeCollection() {
        List<String> articleLinkList = urlService.getArticleLinksAsList();

        if (articleLinkList.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        for (String articleLink : articleLinkList) {
            Optional<Like> like = crawlerService.articleLinkToLike(articleLink);

            if (!like.isPresent()) {

                // TODO : error/log handling
                // TODO : throw custom exception

                throw new ResourceNotFoundException();
            }

            likeRepository.save(like.get());
        }
    }
}
