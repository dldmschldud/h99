package com.h99.member.service;

import com.h99.member.entity.User;
import com.h99.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;
    public String changeRole(Long userId){

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return "해당유저가 없습니다";
        }
        User user = optionalUser.get();
        log.info(user.getRole().name());
        user.changeRole();
        log.info(user.getRole().name());
        return "동영상 업로드 가능";
    }
}
