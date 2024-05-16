package com.example.sign.controller;

import com.example.sign.config.JwtUtil;
import com.example.sign.entity.User;
import com.example.sign.entity.dto.CheckDuplicateDto;
import com.example.sign.entity.dto.LoginDto;
import com.example.sign.entity.dto.SignUpUserDto;
import com.example.sign.service.CustomUserDetailsService;
import com.example.sign.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// https://jie0025.tistory.com/326
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
    public Map<String, String> signup(@RequestBody SignUpUserDto signUpUserDto) {
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
		public Map<String, String> login(@RequestBody LoginDto loginDto, HttpServletResponse response) throws Exception {
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

				Cookie cookie = new Cookie("token", token);
				cookie.setHttpOnly(true);
				cookie.setSecure(true); // HTTPS에서만 전송, 로컬 테스트 시에는 false로 설정
				cookie.setPath("/");
				cookie.setMaxAge(3600); // 쿠키 만료 시간 설정 (단위: 초)
				response.addCookie(cookie);

				return Map.of(
								"code", "200",
								"message", "로그인 성공"
				);
		}


    @PostMapping("/checkduplicate")
    public Map<String, String> checkDuplicate(@RequestBody CheckDuplicateDto checkDuplicateDto) {
        Map<String, String> map = new HashMap<>();
        try {
            userService.checkduplicate(checkDuplicateDto);
            map.put("code", "200");
            map.put("message", "사용 가능한 이메일입니다.");
        } catch (RuntimeException e) {
            map.put("code", "409");
            map.put("message", "200");
        }
        return map;
    }
}