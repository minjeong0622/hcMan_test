package com.heechang.hcMan.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmailVerificationToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;        // 6자리 코드

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Instant expiryDate;
}