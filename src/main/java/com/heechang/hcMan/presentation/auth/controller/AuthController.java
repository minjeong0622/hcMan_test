package com.heechang.hcMan.presentation.auth.controller;

import com.heechang.hcMan.JwtUtil;
import com.heechang.hcMan.domain.user.entity.User;
import com.heechang.hcMan.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String credential = credentials.get("username");
        String password   = credentials.get("password");

        log.debug("로그인 요청: {}", credential);

        // 1) loginId or businessRegNumber 중 하나라도 존재해야 한다
        if (!userRepository.existsByLoginIdOrBusinessRegNumber(credential)) {
            log.warn("로그인 실패 – 사용자 없음: {}", credential);
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "존재하지 않는 사용자입니다."));
        }

        // 2) 비밀번호 검증
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credential, password)
            );
        } catch (BadCredentialsException ex) {
            log.warn("로그인 실패 – 비밀번호 불일치: {}", credential);
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "비밀번호가 올바르지 않습니다."));
        } catch (AuthenticationException ex) {
            log.error("로그인 실패 – 인증 오류: {}", ex.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "인증에 실패했습니다."));
        }

        // 3) 인증 성공 → 실제 User 엔티티 조회
        User user = userRepository.findByLoginId(credential)
                .or(() -> userRepository.findByBusinessRegNumber(credential))
                .orElseThrow(() -> new IllegalStateException("인증 후 사용자 조회 실패"));

        String accessToken  = jwtUtil.generateToken(user.getLoginId(), user.getId(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getLoginId());

        log.info("로그인 성공: {} (id={})", user.getLoginId(), user.getId());
        return ResponseEntity.ok(Map.of(
                "token",        accessToken,
                "refreshToken", refreshToken
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");
        if (jwtUtil.validateToken(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            User user = userRepository.findByLoginId(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String newAccessToken = jwtUtil.generateToken(user.getLoginId(), user.getId(), user.getRole());
            String newRefreshToken = jwtUtil.generateRefreshToken(user.getLoginId());
            log.info("토큰 갱신 성공: username={}", user.getLoginId());
            return ResponseEntity.ok(Map.of("token", newAccessToken, "refreshToken", newRefreshToken));
        }
        log.warn("잘못된 리프레시 토큰: {}", refreshToken);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            log.warn("프로필 조회 실패: 인증 정보 없음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        Optional<User> user = userRepository.findByLoginId(userDetails.getUsername());
        if (user.isEmpty()) {
            log.warn("프로필 조회 실패: 사용자 없음, username={}", userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        log.info("프로필 조회 성공: username={}", userDetails.getUsername());
        return ResponseEntity.ok(user.get());
    }
}
