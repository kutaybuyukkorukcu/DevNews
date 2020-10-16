package com.scalx.devnews.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "stories")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Story extends BaseEntity implements Serializable {

    @Column(name = "title")
    private String title;

    @Column(name = "main_topic")
    private String mainTopic;

    @Column(name = "url")
    private String url;

    @Column(name = "author")
    private String author;

    @Column(name = "text")
    private String text;

    @Column(name = "upvote_count")
    private int upvoteCount;

    @Column(name = "comment_count")
    private int commentCount;

    @Column(name = "created")
    private int created;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "story")
    private List<Comment> comments;
}
