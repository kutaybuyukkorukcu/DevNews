package com.scalx.devnews.service;

import com.scalx.devnews.entity.Like;
import com.scalx.devnews.entity.Url;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.repository.LikeRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
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
    public void test_addLike_whenLikeIsNotPresent() {

        doThrow(new NullPointerException())
                .when(likeService).addLike(null);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> {
                    likeService.addLike(null);
                });

        verify(likeRepository).save(null);
        verifyNoMoreInteractions(likeService);
    }

    @Test
    public void test_addLike_whenLikeIsPresent() {

        Like like = new Like("What's new with Java 11", "Development");

        likeService.addLike(like);

        verify(likeRepository).save(any(Like.class));
        verifyNoMoreInteractions(likeService);
    }

    @Test
    public void test_getLikes_whenFindAllIsNotPresent() {

        when(likeRepository.findAll()).thenReturn(null);

        List<Like> expectedLikeList = likeService.getLikes();

        assertThat(new ArrayList<Like>()).isEqualTo(expectedLikeList);

        verify(likeRepository).findAll();
        verifyNoMoreInteractions(likeService);
    }

    @Test
    public void test_getLikes_whenFindAllIsPresent() {

        List<Like> likeList = new ArrayList<>();
        likeList.add(new Like("What's new with Java 11", "Development"));
        likeList.add(new Like("Comprehensive guide to unit testing", "Development"));

        likeList.get(0).setActive(true);
        likeList.get(1).setActive(false);

        when(likeRepository.findAll()).thenReturn(likeList);

        List<Like> expectedLikeList = likeService.getLikes();

        assertThat(likeList).isEqualTo(expectedLikeList);

        verify(likeRepository).findAllByActive();
        verifyNoMoreInteractions(likeService);
    }

    @Test
    public void test_getLikesByActive_whenFindAllByActiveIsNotPresent() {

        when(likeRepository.findAllByActive()).thenReturn(null);

        List<Like> expectedLikeList = likeService.getLikesByActive();
        List<Like> likeList = new ArrayList<>();

        assertThat(likeList).isEqualTo(expectedLikeList);

        verify(likeRepository).findAllByActive();
        verifyNoMoreInteractions(likeService);
    }

    @Test
    public void test_getLikesByActive_whenFindAllByActiveIsPresent() {

        List<Like> likeList = new ArrayList<>();
        likeList.add(new Like("What's new with Java 11", "Development"));
        likeList.add(new Like("Comprehensive guide to unit testing", "Development"));

        likeList.get(0).setActive(true);
        likeList.get(1).setActive(true);

        when(likeRepository.findAllByActive()).thenReturn(likeList);

        List<Like> expectedLikeList = likeService.getLikesByActive();

        assertThat(likeList).isEqualTo(expectedLikeList);

        verify(likeRepository).findAllByActive();
        verifyNoMoreInteractions(likeService);
    }

    @Test
    public void test_addLikedArticlesIntoLikeCollection_whenGetArticleLinksAsListIsNotPresent() {

        List<String> articleLinkList = new ArrayList<>();

        when(urlService.getArticleLinksAsList()).thenReturn(articleLinkList);

        doThrow(new ResourceNotFoundException())
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

        String articleLink = "www.infoq.com/Whats-new-with-java-11";

        when(crawlerService.articleLinkToLike(articleLink)).thenReturn(Optional.empty());

        doThrow(new ResourceNotFoundException())
                .when(likeService).addLikedArticlesIntoLikeCollection();

        verify(urlService).getArticleLinksAsList();
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

    @Test
    public void test_getLikeByTitle_whenFindByTitleIsNotPresent() {

        String title = "";

        when(likeRepository.findByTitle(title)).thenReturn(null);

        Like like = likeService.getLikeByTitle(title).get();

        assertThat(like).isEqualTo(Optional.empty());

        verify(likeRepository).findByTitle(title);
        verifyNoMoreInteractions(likeService);
    }

    @Test
    public void test_getLikeByTitle_whenFindByTitleIsPresent() {

        String title = "What's new with Java 11";

        Like like = new Like("What's new with Java 11", "Development");

        when(likeRepository.findByTitle(title)).thenReturn(like);

        Like expectedLike = likeService.getLikeByTitle(title).get();

        assertThat(like).isEqualTo(expectedLike);

        verify(likeRepository).findByTitle(title);
        verifyNoMoreInteractions(likeService);
    }


}
