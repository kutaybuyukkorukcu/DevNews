package com.scalx.devnews.service;

import com.scalx.devnews.entity.Article;
import com.scalx.devnews.repository.ArticleRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    @Test
    public void test_addArticle_whenArticleIsNotPresent() {

        // Repository should throw NullPointerException instead of Service impl.
        doThrow(new NullPointerException())
                .when(articleRepository).save(null);

        // Replace the asssertThatExceptionOfType with JUnit AssertThrows
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> {
                    articleService.addArticle(null);
                });

        verify(articleRepository).save(null);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    public void test_addArticle_whenArticleIsPresent() {

        Article article = new Article("What's new with Java 11",
                "Development", "Author", "Development|Java",
                "www.infoq.com/Whats-new-with-Java-11");

        articleService.addArticle(article);

        verify(articleRepository).save(any(Article.class));
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    public void test_getArticles_whenFindAllIsNotPresent() {

        when(articleRepository.findAll()).thenReturn(null);

        List<Article> articleList = articleService.getArticles();
        List<Article> expectedArticleList = new ArrayList<>();

        assertThat(articleList).isEqualTo(expectedArticleList);

        verify(articleRepository).findAll();
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    public void test_getArticles_whenFindAllIsPresent() {

        List<Article> articleList = new ArrayList<>();

        articleList.add(new Article("What's new with Java 11",
                "Development", "Author", "Development|Java",
                "www.infoq.com/Whats-new-with-Java-11"));
        articleList.add(new Article("Comprehensive Guide to Unit Testing",
                "Development", "Author", "Development|Java",
                "www.dzone.com/Comprehensive-guide-to-unit-testing"));

        when(articleRepository.findAll()).thenReturn(articleList);

        List<Article> expectedArticleList = articleService.getArticles();

        assertThat(articleList).isEqualTo(expectedArticleList);

        verify(articleRepository).findAll();
        verifyNoMoreInteractions(articleRepository);
    }
}
