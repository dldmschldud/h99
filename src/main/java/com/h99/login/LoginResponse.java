package com.h99.login;

import com.h99.member.entity.User;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final String token;
    private final User user;

    public LoginResponse(String token, User user){
        this.token = token;
        this.user = user;
    }
}
