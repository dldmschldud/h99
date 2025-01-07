package com.videoservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StopRequestDto {
    private Long userId;
    private Long pauseTime;
}
