package com.h99.video.domain;

import com.h99.member.entity.User;
import com.h99.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Entity
@Getter
@RequiredArgsConstructor
public class VideoRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "end_time")
    private Long endTime;

    @Column(name="user_duration")
    private Long userDuration;

    public VideoRecord(Video video, User user){ // 사용자가 동영상 본적없을때 처음 videoRecord 초기화상태
        this.video = video;
        this.user = user;
        this.startTime = 0L;
        this.endTime = 0L;
        this.userDuration = this.endTime-this.startTime;
    }

    public void updateStartTime(Long endTime){
        //봤던 영상을 다시 재생하는경우 재생이 끝났던 부분부터 다시 재생
        //조회수는 증가하지 않는다
        this.startTime = endTime;
    }

    public void updateEndTime(Long currentTime){
        this.endTime = currentTime;
        this.userDuration = this.endTime - this.startTime;

        //사용자가 영상을 멈추면 현재까지 재생한 시간을 동여앙의 총 재생시간에 더한다
        this.video.addPlayTime(this.userDuration);

        //사용자가 영상을 끝까지 다 보면 다음 재생을 위해 초기화한다
        if (currentTime >= this.video.getVideoDuration()){
            this.startTime = 0L;
            this.endTime = 0L;
            this.userDuration = 0L;
        }
    }


}
