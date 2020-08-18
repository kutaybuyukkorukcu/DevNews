package com.scalx.devnews.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;

@Entity
@Data
public class Article extends AbstractAuditable {

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

//    @Column(name = "is_new")
//    private boolean isNew;
}
