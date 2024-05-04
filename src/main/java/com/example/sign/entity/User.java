package com.example.sign.entity;

import com.example.sign.entity.dto.SignUpUserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {

    public User(String email, String password, String phoneNumber, String authenticateCode, boolean isEmailDuplicated, boolean isAdministrator, Date signUpDate) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.authenticateCode = authenticateCode;
        this.isEmailDuplicated = isEmailDuplicated;
        this.isAdministrator = isAdministrator;
        this.signUpDate = signUpDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    //이메일 인증을 받았나 확인
    @Column(nullable = false)
    private String authenticateCode;

    @Column(nullable = false)
    private boolean isEmailDuplicated;

     //컬럼명 대문자로 적으면 뒤진다
    @Column
    private boolean isAdministrator;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date signUpDate;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }


    public boolean isAccountNonExpired() {
        return false;
    }


    public boolean isAccountNonLocked() {
        return false;
    }


    public boolean isCredentialsNonExpired() {
        return false;
    }


    public boolean isEnabled() {
        return false;
    }

    public static User from(SignUpUserDto signUpUserDto) {
        return new User(
                signUpUserDto.getEmail(),
                signUpUserDto.getPassword(),
                signUpUserDto.getPhoneNumber(),
                signUpUserDto.getAuthenticateCode(),
                signUpUserDto.isEmailDuplicated(),
                false,
                signUpUserDto.getSignUpDate()
        );
    }
}
