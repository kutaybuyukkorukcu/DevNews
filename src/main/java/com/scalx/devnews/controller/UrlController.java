package com.scalx.devnews.controller;

import com.scalx.devnews.dto.url.UrlRequest;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Url;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.service.CrawlerService;
import com.scalx.devnews.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FieldSetter fieldSetter;

    // TODO : We don't wanna return urls from file instead of database.

//    @RequestMapping(value = "/urls", method = RequestMethod.GET)
//    public ResponseEntity<?> getUrls() {
//
//        List<String> articleLinkList;
//
//        try {
//            articleLinkList = crawlerService.getArticleLinksFromFileAsList();
//        } catch (IOException e) {
//            return ResponseEntity.ok(new Article());
//        }
//
//        if (articleLinkList.isEmpty()) {
//            return ResponseEntity.ok(new Article());
//        }
//
//        for (String articleLink : articleLinkList) {
//            Optional<Url> url = urlService.articleLinkToUrl(articleLink);
//
//            if (!url.isPresent()) {
//                return ResponseEntity.ok(new Article());
//            }
//
//            urlService.save(url.get());
//        }
//
//        List<String> allArticleLinkList = urlService.getArticleLinksAsList();
//
//        if (allArticleLinkList.isEmpty()) {
//            return ResponseEntity.ok(new Article());
//        }
//
//        return ResponseEntity.ok(new Article());
//    }

    @RequestMapping(value = "/urls", method = RequestMethod.GET)
    public ResponseEntity<?> getUrls() {

        List<Url> urlList = urlService.getUrlsByActive();

        if (urlList.isEmpty()) {
            return ResponseEntity.ok(new Url());
        }

        return ResponseEntity.ok(urlList);
    }

    @RequestMapping(value = "/urls", method = RequestMethod.POST)
    public ResponseEntity<?> postUrl(@RequestBody UrlRequest urlRequest) {

        Url url = modelMapper.map(urlRequest, Url.class);

        url.setCreatedBy("laal");
        url.setCreatedDate(java.sql.Date.valueOf(LocalDate.now()));
        url.setActive(true);
        url.setLastModifiedBy("laal");
        url.setLastModifiedDate(java.sql.Date.valueOf(LocalDate.now()));

        urlService.save(url);

        return ResponseEntity.ok(url);
    }
}
