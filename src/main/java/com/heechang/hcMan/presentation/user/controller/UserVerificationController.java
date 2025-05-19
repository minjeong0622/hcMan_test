package com.heechang.hcMan.presentation.user.controller;

import com.heechang.hcMan.domain.user.application.EmailVerificationService;
import com.heechang.hcMan.domain.user.application.UserRegistrationService;
import com.heechang.hcMan.domain.user.dto.UserRegistrationRequestDTO;
import com.heechang.hcMan.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserVerificationController {

    private final EmailVerificationService verificationService;
    private final UserRegistrationService   registrationService;

    // 1) 코드 발송
    @PostMapping("/send-code")
    public ResponseEntity<Void> sendCode(@RequestBody Map<String,String> body) {
        verificationService.sendVerificationCode(body.get("email"));
        return ResponseEntity.ok().build();
    }

    // 2) 코드 확인
    @PostMapping("/verify-code")
    public ResponseEntity<Void> verifyCode(@RequestBody Map<String,String> body) {
        verificationService.confirmCode(body.get("email"), body.get("code"));
        return ResponseEntity.ok().build();
    }

    // 3) 인증 완료 후 회원가입 → 메일발송 통합
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegistrationRequestDTO dto) {
        // 1) 코드 확인을 이미 거친 상태라고 가정
        //    (필요시 토큰 재검증 로직 추가)
        User saved = registrationService.registerAndSendWelcome(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}