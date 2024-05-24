package com.example.sign.service;

import com.example.sign.entity.User;
import com.example.sign.entity.UserRepository;
import com.example.sign.entity.dto.CheckDuplicateDto;
import com.example.sign.entity.dto.LoginDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

//비지니스 로직 넣기
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //로직을 넣을게 없어서 바이패스 함
    public void signUp(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public void login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user == null) {
            throw new RuntimeException("유저가 없습니다");
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다");
        }
    }

    public void checkduplicate(CheckDuplicateDto checkDuplicateDto) {
        User user = userRepository.findByEmail(checkDuplicateDto.getEmail());
        if (user != null) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }
    }
}

