package com.example.nbcplusweekassignment.global.security;

import com.example.nbcplusweekassignment.global.exception.user.NotFoundUserException;
import com.example.nbcplusweekassignment.user.entity.User;
import com.example.nbcplusweekassignment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsImpl getUserDetails(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);

        return new UserDetailsImpl(user);
    }
}
