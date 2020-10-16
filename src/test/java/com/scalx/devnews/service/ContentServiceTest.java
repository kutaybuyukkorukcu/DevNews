package com.scalx.devnews.service;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.entity.Like;
import com.scalx.devnews.repository.ArticleRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ContentServiceTest {

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    @Test
    public void test_articleLinkToLike_whenArticleLinkIsNotPresent() {

        String articleLink = "";

        when(articleRepository.findByArticleLink(articleLink)).thenReturn(null);

        Optional<Like> like = articleService.articleLinkToLike(articleLink);

        assertThat(like).isEqualTo(Optional.empty());

        verify(articleRepository).findByArticleLink(articleLink);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    public void test_articleLinkToLike_whenArticleLinkIsPresent() {

        String articleLink = "www.infoq.com/Whats-new-with-Java-11";

        Article article = new Article("Whats new with Java 11", "Development", "Author",
                "Development|Java", "www.infoq.com/Whats-new-with-Java-11");


        when(articleRepository.findByArticleLink(articleLink)).thenReturn(article);

        Like like = articleService.articleLinkToLike(articleLink).get();

        assertThat(like.getTitle()).isEqualTo("Whats new with Java 11");
        assertThat(like.getMainTopic()).isEqualTo("Development");

        verify(articleRepository).findByArticleLink(articleLink);
        verifyNoMoreInteractions(articleRepository);
    }
}
