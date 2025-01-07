package com.videoservice.service;


import com.common.dto.UserDto;
import com.userservice.domain.User;
import com.userservice.repository.UserRepository;
import com.videoservice.client.UserServiceClient;
import com.videoservice.domain.Video;
import com.videoservice.domain.VideoRecord;
import com.videoservice.repository.VideoRecordRepository;
import com.videoservice.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    //private final UserRepository userRepository;
    private final VideoRecordRepository videoRecordRepository;
    private final UserServiceClient userServiceClient;

    @Transactional
    public void playVideo(Long videoId, Long userId){
        Video video = videoRepository.findById(videoId)
                .orElseThrow(()-> new EntityNotFoundException("video not found with id = "+videoId));

        /*
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("user not found with id = "+userId));*/

        // User 서비스 API 호출
        UserDto user = userServiceClient.getUser(userId);
        if (user == null) {
            throw new EntityNotFoundException("user not found with id = " + userId);
        }

        VideoRecord videoRecord = videoRecordRepository.findByVideoIdAndUserId(video.getId(),userId)
                .orElseGet(() -> {
                    VideoRecord newVideoRecord = new VideoRecord(video,userId);
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
