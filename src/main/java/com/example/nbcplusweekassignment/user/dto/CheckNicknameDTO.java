package com.example.nbcplusweekassignment.user.dto;

import jakarta.validation.constraints.NotBlank;

public class CheckNicknameDTO {

    public record Request(
       @NotBlank String nickname
    ) {}

}
