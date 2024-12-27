package com.h99.video.domain;

import com.h99.member.entity.User;
import com.h99.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@RequiredArgsConstructor
public class Video extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //전체 조회수 (유튜브에서의 총 조회수)
    @Column(name="video_views")
    private Long videoViews;

    //총 재생시간
    @Column(name="video_play_time")
    private Long videoPlayTime;

    //전체 동영상 길이
    @Column(name="video_duration")
    private Long videoDuration;

    //광고 총 조회수
    @Column(name="advertisement_views")
    private Long advertisementsViews = 0L;

    public Set<Long> getAdvertisementPoints(){
        Set<Long> adPoints = new HashSet<>();
        adPoints.add(300L);
        for(Long time=900L; time<videoDuration; time+=600L){
            adPoints.add(time);
        }
        return adPoints;
    }
    public void increaseViewCount(){
        this.videoViews++;
    }

    public void increaseAdViewCount(){this.advertisementsViews++;}
    public void addPlayTime(Long userDuration){
        this.videoPlayTime += userDuration;
    }


}
