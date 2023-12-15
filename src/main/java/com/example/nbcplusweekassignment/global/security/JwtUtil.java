package com.example.nbcplusweekassignment.global.security;

import com.example.nbcplusweekassignment.global.exception.jwt.EmptyClaimsJWTException;
import com.example.nbcplusweekassignment.global.exception.jwt.ExpiredJWTException;
import com.example.nbcplusweekassignment.global.exception.jwt.InvalidJWTSignatureException;
import com.example.nbcplusweekassignment.global.exception.jwt.UnsupportedJWTException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private static final String COOKIE_NAME = "jwtToken";

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public Cookie createCookie(Long userId, boolean forLogin) {

        Date date = new Date();
        long expiredTime = forLogin ? 240 * 60 * 1000L : 0;

        String cookieValue = createToken(userId, expiredTime);

        Cookie cookie = new Cookie(COOKIE_NAME, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge((int) expiredTime / 1000);

        return cookie;
    }

    public String createToken(Long userId, long expiredTime) {
        Date date = new Date();

        return Jwts.builder()
                        .setSubject(String.valueOf(userId))
                        .setExpiration(new Date(date.getTime() + expiredTime))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public Claims getUserInfo(String tokenValue) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenValue).getBody();
    }

    public String getJwtFromCookies(Cookie[] cookies) {

        if(cookies == null) return null;

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    public boolean validateToken(String tokenValue) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenValue);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            throw new InvalidJWTSignatureException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredJWTException();
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJWTException();
        } catch (IllegalArgumentException e) {
            throw new EmptyClaimsJWTException();
        }
    }
}
