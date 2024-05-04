package com.example.sign.entity;

import com.example.sign.entity.dto.SignUpUserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {

    public User(String email, String password, String phoneNumber, boolean isAdministrator, Date signUpDate) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
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

    @Column
    private boolean isAdministrator;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date signUpDate;

    public static User from(SignUpUserDto signUpUserDto) {
        return new User(
                signUpUserDto.getEmail(),
                signUpUserDto.getPassword(),
                signUpUserDto.getPhoneNumber(),
                false,
                signUpUserDto.getSignUpDate()
        );
    }
}
