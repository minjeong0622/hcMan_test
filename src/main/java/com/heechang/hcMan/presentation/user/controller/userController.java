package com.heechang.hcMan.presentation.user.controller;

import com.heechang.hcMan.domain.user.entity.User;
import com.heechang.hcMan.domain.user.repository.UserRepository;
import com.heechang.hcMan.domain.user.application.CustomUserDetailsService;
import com.heechang.hcMan.domain.user.application.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class  userController {

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // 1) 중복 체크용 엔드포인트 추가
    //    GET /api/users/check?businessRegNumber=111-11-11111
    @GetMapping("/check")
    public ResponseEntity<Void> checkBusinessRegNumber(@RequestParam String businessRegNumber) {
        boolean exists = userRepository.existsByBusinessRegNumber(businessRegNumber);
        if (exists) {
            // 중복일 땐 409
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        // 사용 가능하면 200
        return ResponseEntity.ok().build();
    }

    // 전체 사용자 조회
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // 특정 사용자 조회
    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 이름/아이디 검색
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchPartners(@RequestParam String query) {
        List<User> users = customUserDetailsService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    // 신규 업체 등록
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        throw new UnsupportedOperationException("이메일 인증 흐름을 사용하세요");
    }

    // 사용자 수정
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setCompanyName(user.getCompanyName());
                    existing.setPassword(user.getPassword());
                    existing.setRole(user.getRole());
                    return ResponseEntity.ok(userRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 사용자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
