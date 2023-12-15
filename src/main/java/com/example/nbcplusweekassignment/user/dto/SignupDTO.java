package com.example.nbcplusweekassignment.user.dto;

import com.example.nbcplusweekassignment.user.entity.User;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SignupDTO {

    public record Request(
            @Pattern(regexp = "^[a-zA-Z0-9]{3,}$") String nickname,
            @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$") String password,
            @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$") String reEnterPassword
    ) {

        public User toEntity(PasswordEncoder passwordEncoder) {
            return User.builder()
                    .nickname(nickname)
                    .password(passwordEncoder.encode(password))
                    .build();
        }
    }
}
