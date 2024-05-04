package com.example.sign.service;

import com.example.sign.entity.User;
import com.example.sign.entity.UserRepository;
import com.example.sign.entity.dto.LoginDto;
import org.springframework.stereotype.Service;

import java.util.Objects;

//비지니스 로직 넣기
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //로직을 넣을게 없어서 바이패스 함
    public void signUp(User user) {
        userRepository.save(user);
    }

    public void login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user == null) {
            throw new RuntimeException("유저가 없습니다");
        }

        if (!Objects.equals(user.getPassword(), loginDto.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다");
        }
    }
}

