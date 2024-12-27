package com.h99.video.dto;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayRequestDto {
    private Long userId;

    public Long getUserId(){
        return userId;
    }
}
