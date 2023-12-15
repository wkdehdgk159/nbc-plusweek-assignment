package com.example.nbcplusweekassignment.global.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        String tokenValue = jwtUtil.getJwtFromCookies(cookies);

        if(StringUtils.hasText(tokenValue)) {
            if(!jwtUtil.validateToken(tokenValue)) {
                setTokenError(response);
                return;
            }
            setAuthentication(tokenValue);
        }
        chain.doFilter(request, response);
    }

    private void setAuthentication(String tokenValue) {

        Claims info = jwtUtil.getUserInfo(tokenValue);
        Long userId = Long.valueOf(info.getSubject());

        UserDetailsImpl userDetails = userDetailsService.getUserDetails(userId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private void setTokenError(HttpServletResponse response) throws IOException {

        response.setStatus(400);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.sendError(400, "유효한 토큰이 아닙니다.");
    }
}
