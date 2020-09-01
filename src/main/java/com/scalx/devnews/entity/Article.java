package com.scalx.devnews.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "articles")
public class Article extends BaseEntity<User> {

    @Column(name = "title")
    private String title;

    @Column(name = "main_topic")
    private String mainTopic;

    @Column(name = "author")
    private String author;
    @Column(name = "related_topics")
    private String relatedTopics;
    @Column(name = "article_link")
    private String articleLink;
}
