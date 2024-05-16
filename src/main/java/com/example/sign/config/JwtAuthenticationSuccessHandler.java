package com.example.sign.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JwtUtil jwtUtil = new JwtUtil();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        
				//쿠키 설정 gpt 참고
				Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS에서만 전송, 로컬 테스트 시에는 false로 설정
        cookie.setPath("/");
        cookie.setMaxAge(3600); // 쿠키 만료 시간 설정 (단위: 초)

        response.addCookie(cookie);

    }
}
