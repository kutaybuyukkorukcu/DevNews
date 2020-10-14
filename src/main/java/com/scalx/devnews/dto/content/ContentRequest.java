package com.scalx.devnews.dto.content;

import com.scalx.devnews.dto.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentRequest extends BaseRequest {

    private String content;

    private String link;

    private String topic;

    public ContentRequest(String accountName, String content, String link, String topic) {
        super(accountName);
        this.content = content;
        this.link = link;
        this.topic = topic;
    }
}
