package com.h99.video.service;

import com.h99.member.entity.User;
import com.h99.member.repository.UserRepository;
import com.h99.video.domain.Video;
import com.h99.video.domain.VideoRecord;
import com.h99.video.repository.VideoRecordRepository;
import com.h99.video.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoRecordRepository videoRecordRepository;

    @Transactional
    public void playVideo(Long videoId, Long userId){
        Video video = videoRepository.findById(videoId)
                .orElseThrow(()-> new EntityNotFoundException("video not found with id = "+videoId));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("user not found with id = "+userId));


        VideoRecord videoRecord = videoRecordRepository.findByVideoIdAndUserId(video.getId(),user.getId())
                .orElseGet(() -> {
                    VideoRecord newVideoRecord = new VideoRecord(video,user);
                    return videoRecordRepository.save(newVideoRecord);
                });




        //처음재생하는 경우 - 최초시청의 경우랑, 끝까지 다보고 다시 시청하는경우만 조회수가 1증가함 봤던곳 부터 다시보는것은 조회수가 오르지 않는다
        if (videoRecord.getEndTime() == 0){
            video.increaseViewCount();
        } else{//봤던곳부터 다시 재생
            videoRecord.updateStartTime(videoRecord.getEndTime());
        }
    }

    @Transactional
    public void stopVideo(Long videoId, Long userId, Long pauseTime){
        VideoRecord videoRecord = videoRecordRepository.findByVideoIdAndUserIdWithVideo(videoId,userId)
                .orElseThrow(() -> new EntityNotFoundException("videoRecord not found"));

        Video video = videoRecord.getVideo();
        if (videoRecord.getEndTime() >= video.getVideoDuration()) {
            for (Long adPoint : video.getAdvertisementPoints()) {
                video.increaseAdViewCount();
            }
        } else {
            for (Long adPoint : video.getAdvertisementPoints()) {
                if (adPoint > videoRecord.getStartTime() && adPoint <= pauseTime) {
                    video.increaseAdViewCount();
                }
            }
        }
        videoRecord.updateEndTime(pauseTime);


    }



}
