package com.heechang.hcMan.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromAddress;   // properties 에서 읽어올 발신자

    /**
     * 신규 업체 등록 안내 메일 발송
     * @param to 수신 이메일
     * @param loginId 부여된 로그인 ID
     * @param tempPassword 생성된 임시 비밀번호
     */
    public void sendRegistrationEmail(String to, String loginId, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);  // 명시적으로 발신자 설정
        message.setTo(to);
        message.setSubject("[HCMan] 신규 업체 등록 안내");
        message.setText(
                "안녕하세요, HCMan 입니다.\n" +
                        "신규 업체 등록이 완료되었습니다.\n\n" +
                        "로그인 정보 안내\n" +
                        "ID: " + loginId + "\n" +
                        "PW: " + tempPassword + "\n\n" +
                        "로그인 후 비밀번호를 반드시 변경해 주세요.\n"
        );
        mailSender.send(message);
    }
}
