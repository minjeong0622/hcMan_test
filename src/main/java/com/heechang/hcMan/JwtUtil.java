package com.heechang.hcMan;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 최소 32바이트 이상의 비밀 키 사용
    private final String SECRET_KEY = "my_super_secret_key_that_is_very_long_and_secure!";
    private final long ACCESS_EXPIRATION_TIME = 1000 * 60 * 60; // 1시간
    private final long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // 액세스 토큰 생성 (username, userId, role 포함)
    public String generateToken(String username, Long userId, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 리프레시 토큰 생성 (username만 포함)
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 username 추출
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // 토큰에서 userId 추출
    public Long extractUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    // 토큰에서 role 추출
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            return getClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰에서 Claims 추출
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
