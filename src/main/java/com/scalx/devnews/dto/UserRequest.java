package com.scalx.devnews.dto;

import lombok.Getter;

@Getter
public class UserRequest {

    private String username;

    private String password;

    private String email;

    private boolean enabled;

    private boolean tokenExpired;
}
