package com.scalx.devnews.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ArticleMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    
    private String title;

    private String mainTopic;

    private String author;
    private String relatedTopics;
    private String articleLink;

    private boolean isNew;


}
