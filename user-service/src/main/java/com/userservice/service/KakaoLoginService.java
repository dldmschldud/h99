package com.userservice.service;

import com.userservice.domain.User;
import com.userservice.dto.LoginResponse;
import com.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public LoginResponse loginOrRegisterUser(String accessToken) {
        // 카카오 사용자 정보 가져오기
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    userInfoUrl,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            Map<String, Object> attributes = response.getBody();
            Long kakaoId = (Long) attributes.get("id");
            Optional<User> optionalUser = userRepository.findByKid(kakaoId);
            User user = optionalUser.orElseGet(() -> userRepository.save(new User(kakaoId)));
            //return user;
            String jwtToken = jwtService.generateToken(user);
            return new LoginResponse(jwtToken,user);

        } catch (Exception e) {
            throw new RuntimeException("Failed to get Kakao user info", e);
        }
    }


}
