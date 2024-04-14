package com.example.sign.entity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sign.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}