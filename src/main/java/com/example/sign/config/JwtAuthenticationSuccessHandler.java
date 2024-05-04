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
        response.setHeader("Authorization", "Bearer " + token);
    }
}
