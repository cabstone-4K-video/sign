package com.example.sign.userservice;

import com.example.sign.entity.User;
import com.example.sign.entity.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserRepository userRepository;
    @Test
    public void test() {
        System.out.println(userRepository.findById(1L).get().toString());
    }
}
