package com.scalx.devnews.dto.user;

import com.scalx.devnews.dto.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest extends BaseRequest {

    private String username;

    private String password;

    private String email;

    private boolean enabled;

    private boolean tokenExpired;
}
