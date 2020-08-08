package com.scalx.devnews.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;

@Entity
@Table(name = "article")
@Data
public class Article extends AbstractAuditable {

    @Column(name = "article_id")
    private int articleId;

    @Column(name = "title")
    private String title;

    @Column(name = "main_topic")
    private String mainTopic;

    @Column(name = "author")
    private String author;
    @Column(name = "relatedTopics")
    private String relatedTopics;
    @Column(name = "article_link")
    private String articleLink;

//    private boolean isNew;
}
