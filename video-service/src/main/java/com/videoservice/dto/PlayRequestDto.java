package com.videoservice.dto;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayRequestDto {
    private Long userId;

    public Long getUserId(){
        return userId;
    }
}
