package com.scalx.devnews.dto.content;

import com.scalx.devnews.dto.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentRequest {

    private String link;

    private String topic;

    public ContentRequest(String link, String topic) {
        this.link = link;
        this.topic = topic;
    }
}
