package com.scalx.devnews.service;

import com.scalx.devnews.entity.Url;
import com.scalx.devnews.repository.UrlRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UrlServiceTest {

    @Mock
    UrlRepository urlRepository;

    @InjectMocks
    UrlService urlService;

    @Test
    public void test_addUrl_whenUrlIsNotPresent() {

        doThrow(new NullPointerException())
                .when(urlRepository).save(null);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> {
                   urlService.addUrl(null);
                });

        verify(urlRepository).save(null);
        verifyNoMoreInteractions(urlRepository);
    }

    @Test
    public void test_addUrl_whenUrlIsPresent() {

        Url url = new Url("www.infoq.com/Whats-new-with-Java-11");

        urlService.addUrl(url);

        verify(urlRepository).save(any(Url.class));
        verifyNoMoreInteractions(urlRepository);
    }

    @Test
    public void test_getUrls_whenFindAllIsNotPresent() {

    }

    @Test
    public void test_getUrls_whenFindAllIsPresent() {

    }

    @Test
    public void test_getUrlsByActive_whenFindAllByActiveIsNotPresent() {

    }

    @Test
    public void test_getUrlsByActive_whenFindAllByActiveIsPresent() {

    }

    @Test
    public void test_getArticleLinksAsList_whenFindAllByActiveIsNotPresent() {

        when(urlRepository.findAllByActive()).thenReturn(null);

        List<String> expectedArticleLinkList = urlService.getArticleLinksAsList();

        assertThat(expectedArticleLinkList).isEqualTo(new ArrayList<>());

        verify(urlRepository).findAllByActive();
    }

    @Test
    public void test_getArticleLinksAsList_whenFindAllByActiveIsPresent() {

        Url url = new Url("www.infoq.com/Whats-new-with-Java-11");
        Url url1 = new Url("www.dzone.com/Comprehensive-guide-to-unit-testing");

        List<String> articleLinkList = Arrays.asList(
                "www.infoq.com/Whats-new-with-Java-11",
                "www.dzone.com/Comprehensive-guide-to-unit-testing"
        );

        when(urlRepository.findAllByActive()).thenReturn(Arrays.asList(url, url1));

        List<String> expectedArticleLinkList = urlService.getArticleLinksAsList();

        assertThat(articleLinkList).isEqualTo(expectedArticleLinkList);

        verify(urlRepository).findAllByActive();
        verifyNoMoreInteractions(urlRepository);
    }

    @Test
    public void test_articleLinkToUrl_whenArticleLinkIsNotPresent() {
        Url url = urlService.articleLinkToUrl(null);

        assertThat(url.getArticleLink()).isEqualTo(null);
    }

    @Test
    public void test_articleLinkToUrl_whenArticleLinkIsPresent() {
        Url url = urlService.articleLinkToUrl("www.infoq.com/Whats-new-with-Java-11");

        assertThat(url.getArticleLink()).isEqualTo("www.infoq.com/Whats-new-with-Java-11");
    }
}
