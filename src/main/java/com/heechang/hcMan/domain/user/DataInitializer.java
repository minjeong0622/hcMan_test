package com.heechang.hcMan.domain.user;

import com.heechang.hcMan.domain.user.entity.User;
import com.heechang.hcMan.domain.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 변경

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) { // 변경
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() > 0) {
            return;
        }

        userRepository.save(User.builder()
                .loginId("superadmin")
                .password(passwordEncoder.encode("superadminpassword"))
                .role("ROLE_SUPERADMIN")
                .partnerType("기타")
                .country("한국")
                .domesticOrForeign("내수")
                .businessType("법인사업자")
                .companyName("슈퍼관리자테스트회사")
                .businessRegNumber("0000000000")
                .companySize("중소기업")
                .ceoName("관리자")
                .contactEmail("superadmin@heechang.com")
                .build());
    }
}
