package com.scalx.devnews.service;

import com.scalx.devnews.entity.Like;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.repository.LikeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
    public void test_getLikesByActive_whenFindAllByActiveIsNotPresent() {

        when(likeRepository.findAllByActive()).thenReturn(null);

        List<Like> expectedLikeList = likeService.getLikesByActive();

        List<Like> likeList = new ArrayList<>();

        verify(likeRepository).findAllByActive();
        assertThat(likeList).isEqualTo(expectedLikeList);
        verifyNoMoreInteractions(likeService);
    }

    @Test
    public void test_getLikesByActive_whenFindAllByActiveIsPresent() {

        Like like = new Like("What's new with Java 11", "Development");
        Like like1 = new Like("Comprehensive guide to unit testing", "Development");

        List<Like> likeList = new ArrayList<>();
        likeList.add(like);
        likeList.add(like1);

        when(likeRepository.findAllByActive()).thenReturn(likeList);

        List<Like> expectedLikeList = likeService.getLikesByActive();

        verify(likeRepository).findAllByActive();

        assertThat(likeList).isEqualTo(expectedLikeList);
        verifyNoMoreInteractions(likeService);
    }

    @Test
    public void test_addLikedArticlesIntoLikeCollection_whenGetArticleLinksAsListIsNotPresent() {

        List<String> articleLinkList = new ArrayList<>();

        when(urlService.getArticleLinksAsList()).thenReturn(articleLinkList);

        doThrow(new ResourceNotFoundException())
                .doNothing()
                .when(likeService).addLikedArticlesIntoLikeCollection();

        verify(urlService).getArticleLinksAsList();
        verifyNoMoreInteractions(likeService);
    }

    @Test
    public void test_addLikedArticlesIntoLikeCollection_whenArticleLinkToLikeIsNotPresent() {

        List<String> articleLinkList = new ArrayList<>();
        articleLinkList.add("www.infoq.com/Whats-new-with-Java-11");
        articleLinkList.add("www.dzone.com/Comprehensive-guide-to-unit-testing");

        when(urlService.getArticleLinksAsList()).thenReturn(articleLinkList);
        verify(urlService).getArticleLinksAsList();

        String articleLink = "www.infoq.com/Whats-new-with-java-11";

        when(crawlerService.articleLinkToLike(articleLink).get()).thenReturn(null);

        doThrow(new ResourceNotFoundException())
                .doNothing()
                .when(likeService).addLikedArticlesIntoLikeCollection();

        verify(crawlerService).articleLinkToLike(articleLink);
        verifyNoMoreInteractions(likeService);
    }

    @Test
    public void test_addLikedArticlesIntoLikeCollection_whenEverythingIsPresent() {

        List<String> articleLinkList = new ArrayList<>();
        articleLinkList.add("www.infoq.com/Whats-new-with-Java-11");
        articleLinkList.add("www.dzone.com/Comprehensive-guide-to-unit-testing");

        when(urlService.getArticleLinksAsList()).thenReturn(articleLinkList);
        verify(urlService).getArticleLinksAsList();

        String articleLink = "www.infoq.com/Whats-new-with-java-11";
        Like like = new Like("What's new with Java 11", "Development");

        when(crawlerService.articleLinkToLike(articleLink).get()).thenReturn(like);
        verify(crawlerService).articleLinkToLike(articleLink);

        verify(likeRepository).save(like);
        verifyNoMoreInteractions(likeService);
    }
}
