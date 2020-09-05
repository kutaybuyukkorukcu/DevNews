package com.scalx.devnews.dto.url;

import com.scalx.devnews.dto.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UrlRequest extends BaseRequest {

    private String articleLink;
}
