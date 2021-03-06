package com.scalx.devnews.service;

import com.scalx.devnews.entity.Like;
import com.scalx.devnews.entity.Url;
import com.scalx.devnews.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public void addUrl(Url url) {
        urlRepository.save(url);
    }

    public List<Url> getUrls() {
        List<Url> urlList = urlRepository.findAll();

        return urlList != null ? urlList
                : Collections.emptyList();
    }

    public List<Url> getUrlsByActive() {

        List<Url> urlList = urlRepository.findAllByActive();

        return urlList != null ? urlList
                : Collections.emptyList();
    }

    public List<String> getArticleLinksAsList() {

        List<Url> urlList = urlRepository.findAllByActive();

        if (urlList == null) {
            return Collections.emptyList();
        }

        List<String> articleLinkList = new ArrayList<>();

        for (Url url : urlList) {
            articleLinkList.add(url.getArticleLink());
        }

        return articleLinkList;
    }

    public Url articleLinkToUrl(String articleLink) {
        Url url = new Url();

        url.setArticleLink(articleLink);

        return url;
    }
}
