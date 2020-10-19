package com.scalx.devnews.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("story_id")
    private int storyId;

    @JsonProperty("text")
    private String text;

    @JsonProperty("author")
    private String author;

    @JsonProperty("parent_comment_id")
    private int parentCommentId;

    @JsonProperty("created")
    private int created;
}
