package com.scalx.devnews.dto.story;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoryRequest {

    @JsonProperty("topic")
    private String topic;
}
