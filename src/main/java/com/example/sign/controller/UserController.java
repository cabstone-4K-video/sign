package com.example.sign.controller;

import com.example.sign.config.JwtUtil;
import com.example.sign.entity.User;
import com.example.sign.entity.dto.CheckDuplicateDto;
import com.example.sign.entity.dto.LoginDto;
import com.example.sign.entity.dto.SignUpUserDto;
import com.example.sign.service.CustomUserDetailsService;
import com.example.sign.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
public class UserController {
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = new JwtUtil();
    }

    @GetMapping("/")
    public String healthCheck() {
        return "ok";
    }

    @PostMapping("/signup")
    //프론트에서 자동으로 Json으로 바뀜
    public Map<String, String> signup(@Valid @RequestBody SignUpUserDto signUpUserDto) {
        Map<String, String> map = new HashMap<>();
        try {
            userService.signUp(User.from(signUpUserDto));
            // response 보고 두범님과 해결
            map.put("code", "200");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            map.put("code", "405");
        }
        return map;
    }

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginDto loginDto) throws Exception {
        try {
            userService.login(loginDto);
        } catch (Exception e) {
            return Map.of(
                    "code", "401",
                    "message", e.getMessage(),
                    "token", ""
            );
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getEmail());

        final String token = jwtUtil.generateToken(userDetails.getUsername());

        return Map.of(
                "code", "200",
                "message", "로그인 성공",
                "token", token
        );
    }

    @PostMapping("/checkduplicate")
    public Map<String, String> checkDuplicate(@RequestBody CheckDuplicateDto checkDuplicateDto) {
        Map<String, String> map = new HashMap<>();
        userService.checkduplicate(checkDuplicateDto);
        map.put("code", "200");
        map.put("message", "사용 가능한 이메일입니다.");
        return map;
    }
}