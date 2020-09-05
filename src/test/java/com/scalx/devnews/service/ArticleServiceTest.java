package com.scalx.devnews.service;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    @Test
    public void test_addArticle_whenNoArticlesArePresent() {

        doThrow(new NullPointerException())
                .doNothing()
                .when(articleService).save(null);

        verify(articleRepository).save(null);
        verifyNoMoreInteractions(articleService);
    }

    @Test
    public void test_addArticle_whenOneArticleIsPresent() {

        Article article = new Article(1, "What's new with Java 11",
                "Development", "Author", "Development|Java",
                "www.infoq.com/Whats-new-with-Java-11", true);
    }

    @Test
    public void test_getArticles_whenFindAllIsNotPresent() {

    }

    @Test
    public void test_getArticles_whenFindAllIsPresent() {


    }
}
