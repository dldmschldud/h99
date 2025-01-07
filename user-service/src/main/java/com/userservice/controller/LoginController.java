package com.userservice.controller;


import com.userservice.dto.LoginResponse;
import com.userservice.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final KakaoLoginService kakaoLoginService;

    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    @Value("${kakao.client-secret}")
    private String clientSecret;
    @GetMapping("/login/oauth2/kakao")
    public ResponseEntity<?> login(@RequestParam String code){

        // 토큰 요청 URL
        String tokenRequestUrl = "https://kauth.kakao.com/oauth/token";
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code",code);
        if (clientSecret != null && !clientSecret.isEmpty()) {
            params.add("client_secret", clientSecret);}
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        try {
            // 토큰 요청 및 응답 처리
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    tokenRequestUrl,
                    request,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                String accessToken = (String) response.getBody().get("access_token");

                LoginResponse loginResponse = kakaoLoginService.loginOrRegisterUser(accessToken);

                return ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "+ loginResponse.getToken())
                        .body(loginResponse);
            } else {
                return ResponseEntity.status(response.getStatusCode())
                        .body("토큰 요청 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("로그인 처리 중 오류 발생"  + e.getMessage());
        }
    }




}
