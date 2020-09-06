package com.scalx.devnews.service;

import com.scalx.devnews.entity.Like;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service
public class LikeService {

    @Autowired
    private UrlService urlService;

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    private LikeRepository likeRepository;

    public void save(Like like) {
        likeRepository.save(like);
    }

    public List<Like> getLikes() {

        List<Like> likeList = likeRepository.findAll();

        if (likeList == null) {
            return Collections.emptyList();
        }

        return likeList;
    }

    public List<Like> getLikesByActive() {

        List<Like> likeList = likeRepository.findAllByActive();

        if (likeList == null) {
            return Collections.emptyList();
        }

        return likeList;
    }

//    public List<Like> getNewLikes() {
//
//        List<Like> likeList = likeRepository.findAll();
//
//        List<Like> activeLikeList = new ArrayList<>();
//
//        for (Like like: likeList) {
//            if (like.isActive() == true) {
//                activeLikeList.add(like);
//            }
//        }
//
//        if (activeLikeList == null) {
//            return Collections.emptyList();
//        }
//
//        return activeLikeList;
//    }

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
