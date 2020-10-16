package com.scalx.devnews.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @Column("title")
    private String title;

    @Column("main_topic")
    private String mainTopic;

    @Column("url")
    private String url;

    @Column("author")
    private String author;

    @Column("text")
    private String text;

    @Column("upvote_count")
    private int upvoteCount;

    @Column("comment_count")
    private int commentCount;

    @Column("created")
    private int created;

    @OneToMany(mappedBy = "stories")
    private List<Comment> comments;
}
