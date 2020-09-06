package com.scalx.devnews.dto.article;

import com.scalx.devnews.dto.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest extends BaseRequest {

    private String title;

    private String mainTopic;

    private String author;

    private String relatedTopics;

    private String articleLink;
}
