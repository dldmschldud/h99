package com.userservice.service;

import com.userservice.domain.User;
import com.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }
}
