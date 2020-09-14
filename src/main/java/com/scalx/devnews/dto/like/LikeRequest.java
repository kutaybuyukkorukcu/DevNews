package com.scalx.devnews.dto.like;

import com.scalx.devnews.dto.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeRequest extends BaseRequest {

    private String title;

    private String mainTopic;

    public LikeRequest(String accountName, String title, String mainTopic) {
        super(accountName);
        this.title = title;
        this.mainTopic = mainTopic;
    }
}
