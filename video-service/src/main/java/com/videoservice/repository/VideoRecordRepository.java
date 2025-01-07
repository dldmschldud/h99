package com.videoservice.repository;


import com.videoservice.domain.VideoRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VideoRecordRepository extends JpaRepository<VideoRecord,Long> {

    Optional<VideoRecord> findByVideoIdAndUserId(Long VideoId, Long UserId);


    @Query("SELECT vr FROM VideoRecord vr JOIN FETCH vr.video WHERE vr.video.id = :videoId AND vr.userId = :userId")
    Optional<VideoRecord> findByVideoIdAndUserIdWithVideo(Long videoId, Long userId);
}
