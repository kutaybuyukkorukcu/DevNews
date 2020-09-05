package com.scalx.devnews.dto;

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
