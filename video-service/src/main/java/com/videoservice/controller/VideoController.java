package com.videoservice.controller;

import com.videoservice.dto.PlayRequestDto;
import com.videoservice.dto.StopRequestDto;
import com.videoservice.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/play/{videoId}")
    public ResponseEntity<Void> playVideo(@PathVariable Long videoId, @RequestBody PlayRequestDto playRequestDto){

        videoService.playVideo(videoId, playRequestDto.getUserId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/stop/{videoId}")
    public ResponseEntity<Void> stopVideo(@PathVariable Long videoId, @RequestBody StopRequestDto stopRequestDto){
        videoService.stopVideo(videoId, stopRequestDto.getUserId(),stopRequestDto.getPauseTime());
        return ResponseEntity.ok().build();
    }

}

