package com.example.nbcplusweekassignment.user.controller;

import com.example.nbcplusweekassignment.user.dto.CheckNicknameDTO;
import com.example.nbcplusweekassignment.user.dto.LoginDTO;
import com.example.nbcplusweekassignment.user.dto.SignupDTO;
import com.example.nbcplusweekassignment.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userservice;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupDTO.Request signupRequestDTO) {

        userservice.signup(signupRequestDTO);

        return ResponseEntity.ok().body("회원가입 성공");
    }

//    //중복 닉네임 확인  -> 을 위한 새로운 API가 필요할까 고민
    @PostMapping("/signup/check-nickname")
    public ResponseEntity<String> checkDuplicateNickname(
            @Valid @RequestBody CheckNicknameDTO.Request requestDTO) {

        userservice.validateEnableNickname(requestDTO.nickname());

        return ResponseEntity.ok().body("사용 가능한 닉네임입니다.");
    }

    //
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO.Request loginRequestDTO, HttpServletResponse response) {

        userservice.login(loginRequestDTO, response);

        return ResponseEntity.ok().body("로그인 성공");
    }

}
