package com.example.nbcplusweekassignment.user.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    public record Request(
            @NotBlank String nickname,
            @NotBlank String password
    ) {
    }

}
