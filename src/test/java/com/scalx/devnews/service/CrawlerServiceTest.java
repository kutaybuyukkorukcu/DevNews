package com.scalx.devnews.service;

import com.scalx.devnews.dto.article.ArticleRequest;
import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Like;
import com.scalx.devnews.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CrawlerServiceTest {

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    CrawlerService crawlerService;

    @Test
    public void test_articleLinkToLike_whenArticleLinkIsNotPresent() {

        String articleLink = "";

        when(articleRepository.findByArticleLink(articleLink)).thenReturn(null);

        Like like = crawlerService.articleLinkToLike(articleLink).get();

        assertThat(like).isEqualTo(Optional.empty());

        verify(articleRepository).findByArticleLink(articleLink);
        verifyNoMoreInteractions(crawlerService);
    }

    @Test
    public void test_articleLinkToLike_whenArticleLinkIsPresent() {

        String articleLink = "www.infoq.com/Whats-new-with-Java-11";

        Article article = new Article("Whats new with Java 11", "Development", "Author",
                "Development|Java", "www.infoq.com/Whats-new-with-Java-11");


        when(articleRepository.findByArticleLink(articleLink)).thenReturn(article);

        Like like = crawlerService.articleLinkToLike(articleLink).get();

        assertThat(like.getTitle()).isEqualTo("Whats new with Java 11");
        assertThat(like.getMainTopic()).isEqualTo("Development");

        verify(articleRepository).findByArticleLink(articleLink);
        verifyNoMoreInteractions(crawlerService);
    }
}
