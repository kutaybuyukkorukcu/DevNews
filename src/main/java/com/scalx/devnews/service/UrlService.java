package com.scalx.devnews.service;

import com.scalx.devnews.entity.Url;
import com.scalx.devnews.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    UrlRepository urlRepository;

    public void save(Url url) {
        urlRepository.save(url);
    }

    public List<Url> getUrls() {
        List<Url> urlList = urlRepository.findAll();

        if (urlList == null) {
            return Collections.emptyList();
        }

        return urlList;
    }

    public List<String> getArticleLinksAsList() {
        List<Url> urlList = urlRepository.findByIsNew();

        if (urlList == null) {
            return Collections.emptyList();
        }

        List<String> articleLinkList = new ArrayList<>();

        for (Url url : urlList) {
            articleLinkList.add(url.getArticleLink());
        }

        return articleLinkList;
    }

    public Optional<Url> articleLinkToUrl(String articleLink) {
        Url url = new Url();

        url.setArticleLink(articleLink);
        url.setIsNew(true);

        return Optional.ofNullable(url);
    }
}
