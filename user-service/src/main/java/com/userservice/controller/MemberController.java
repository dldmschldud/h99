package com.userservice.controller;

import com.common.dto.UserDto;
import com.userservice.domain.User;
import com.userservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MemberController {

    private final MemberService memberservice;

    @PostMapping("/member/upload/{userId}")
    public ResponseEntity<?> changeRole(@PathVariable Long userId){

        String result = memberservice.changeRole(userId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        User user = memberservice.findById(userId);
        UserDto userDto = new UserDto(
                user.getId(),
                user.getKid(),
                user.isUploadEnable(),
                user.getRole().toString()
        );
        return ResponseEntity.ok(userDto);
    }
}
