package com.heechang.hcMan.domain.user.application;

import com.heechang.hcMan.domain.user.entity.EmailVerificationToken;
import com.heechang.hcMan.domain.user.repository.EmailVerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    private final EmailVerificationTokenRepository tokenRepo;
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}") private String fromAddress;

    /** 1) 6자리 코드 생성→DB 저장→이메일 발송 */
    @Transactional
    public void sendVerificationCode(String email) {
        tokenRepo.deleteByEmail(email);

        String code = String.format("%06d", new SecureRandom().nextInt(1_000_000));
        EmailVerificationToken ev = EmailVerificationToken.builder()
                .email(email)
                .token(code)
                .expiryDate(Instant.now().plus(1, ChronoUnit.HOURS))
                .build();
        tokenRepo.save(ev);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAddress);
        msg.setTo(email);
        msg.setSubject("[HCMan] 이메일 인증 코드");
        msg.setText("아래 6자리 인증 코드를 입력하여 이메일을 확인해주세요:\n\n" + code);
        mailSender.send(msg);
    }

    /** 2) 사용자가 입력한 코드 검증 → 만료/불일치 시 예외 */
    @Transactional
    public void confirmCode(String email, String code) {
        EmailVerificationToken ev = tokenRepo.findByEmailAndToken(email, code)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 인증 코드입니다."));
        if (ev.getExpiryDate().isBefore(Instant.now())) {
            throw new IllegalArgumentException("인증 코드가 만료되었습니다.");
        }
        tokenRepo.delete(ev);
    }
}