package com.example.sign.entity.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.Date;

@Data
public class SignUpUserDto {
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 유효하지 않습니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$",
            message = "비밀번호는 영문자, 숫자, 특수문자를 포함한 8~15자리여야 합니다.")
    private String password;

    @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
    private String phoneNumber;

    private Date signUpDate;
}