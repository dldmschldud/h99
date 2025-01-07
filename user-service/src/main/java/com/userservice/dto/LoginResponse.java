package com.userservice.dto;

import com.userservice.domain.User;
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
