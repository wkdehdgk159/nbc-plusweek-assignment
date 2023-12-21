package com.example.nbcplusweekassignment.user.service;

import com.example.nbcplusweekassignment.global.exception.user.AlreadyExistingNicknameException;
import com.example.nbcplusweekassignment.global.exception.user.InvalidUserAndPasswordException;
import com.example.nbcplusweekassignment.global.exception.user.NotFoundUserException;
import com.example.nbcplusweekassignment.global.exception.user.NotSamePasswordException;
import com.example.nbcplusweekassignment.global.exception.user.PasswordContainsNicknameException;
import com.example.nbcplusweekassignment.global.security.JwtUtil;
import com.example.nbcplusweekassignment.user.dto.LoginDTO;
import com.example.nbcplusweekassignment.user.dto.LoginDTO.Request;
import com.example.nbcplusweekassignment.user.dto.SignupDTO;
import com.example.nbcplusweekassignment.user.entity.User;
import com.example.nbcplusweekassignment.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final String BEARER_PREFIX = "Bearer ";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public void signup(SignupDTO.Request signupRequestDTO) {

        //아이디 중복 검증
        validateEnableNickname(signupRequestDTO.nickname());

        //패스워드 유효성 검증(일치하는지, 닉네임이 포함되는지)
        validatePassword(signupRequestDTO);

        //정보 저장
        User user = signupRequestDTO.toEntity(passwordEncoder);
        userRepository.save(user);
    }

    public void validateEnableNickname(String nickname) {

        if(userRepository.findByNickname(nickname).isPresent()) {
            throw new AlreadyExistingNicknameException();
        }
    }

    void validatePassword(SignupDTO.Request signupRequestDTO) {

        if(!signupRequestDTO.password().equals(signupRequestDTO.reEnterPassword())) {
            throw new NotSamePasswordException();
        }

        if(signupRequestDTO.password().contains(signupRequestDTO.nickname())) {
            throw new PasswordContainsNicknameException();
        }
    }

    public void login(LoginDTO.Request loginRequestDTO, HttpServletResponse response) {

        //일치하는 아이디-비밀번호가 있는지 검증
        User user = validateNicknameAndPassword(loginRequestDTO);

        //로그인 성공으로 response에 쿠키 담아주기
        Cookie cookie = jwtUtil.createCookie(user.getId(), true);

        response.addCookie(cookie);
    }

    private User validateNicknameAndPassword(LoginDTO.Request loginRequestDTO) {

        User user = userRepository.findByNickname(loginRequestDTO.nickname())
                .orElseThrow(InvalidUserAndPasswordException::new);

        if(!passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
            throw new InvalidUserAndPasswordException();
        }

        return user;
    }

    public User findUserById(Long userId) {

        return userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
    }
}
