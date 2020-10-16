package com.scalx.devnews.dto.story;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scalx.devnews.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("main_topic")
    private String mainTopic;

    @JsonProperty("url")
    private String url;

    @JsonProperty("author")
    private String author;

    @JsonProperty("text")
    private String text;

    @JsonProperty("upvote_count")
    private int upvoteCount;

    @JsonProperty("comment_count")
    private int commentCount;

    @JsonProperty("created")
    private int created;

    @JsonProperty("comments")
    private List<Comment> comments;
}
