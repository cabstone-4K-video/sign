package com.example.sign.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SignUpUserDto {
    private String email;
    private String password;
    private String phoneNumber;
    private Date signUpDate;
}
