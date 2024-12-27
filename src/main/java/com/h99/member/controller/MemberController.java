package com.h99.member.controller;


import com.h99.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberservice;

    @PostMapping("/member/upload/{userId}")
    public ResponseEntity<?> changeRole(@PathVariable Long userId){

        String result = memberservice.changeRole(userId);

        return ResponseEntity.ok(result);
    }

}
