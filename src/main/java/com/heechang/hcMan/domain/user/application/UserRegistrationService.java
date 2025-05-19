package com.heechang.hcMan.domain.user.application;

import com.heechang.hcMan.domain.user.entity.User;
import com.heechang.hcMan.domain.user.repository.EmailVerificationTokenRepository;
import com.heechang.hcMan.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;          // <- 추가
    private final EmailVerificationTokenRepository tokenRepo; // <- 인증 토큰 repo

    @Transactional
    public User registerAndSendWelcome(User user) {
        // 1) 이메일 인증 토큰 삭제(혹시 남아있다면)
        tokenRepo.deleteByEmail(user.getContactEmail());

        // 2) 기존 중복 체크
        if (userRepository.existsByBusinessRegNumber(user.getBusinessRegNumber())) {
            throw new IllegalArgumentException("이미 등록된 사업자등록번호입니다.");
        }

        // 3) loginId, 임시 비밀번호 세팅
        user.setLoginId(user.getBusinessRegNumber());
        String tempPwd = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(passwordEncoder.encode(tempPwd));
        if (user.getRole() == null) user.setRole("ROLE_USER");

        // 4) 저장
        User saved = userRepository.save(user);

        // 5) 임시 비밀번호 안내 메일 발송
        emailService.sendRegistrationEmail(
                saved.getContactEmail(),
                saved.getLoginId(),
                tempPwd
        );

        return saved;
    }
}