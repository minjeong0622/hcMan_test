package com.heechang.hcMan.domain.user.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_contacts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;           // 성명

    private String phone;          // 전화번호

    private String mobile;         // 휴대폰번호

    private String email;          // 이메일

    private boolean primaryContact; // 기본 담당자 여부
}
