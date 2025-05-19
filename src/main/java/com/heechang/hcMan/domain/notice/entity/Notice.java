package com.heechang.hcMan.domain.notice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // 공지사항 ID

    @Column(nullable = false)
    private String title;           // 제목

    @Lob
    @Column(nullable = false)
    private String content;         // 내용

    @Column(nullable = false)
    private String author;          // 작성자

    // 공지기간
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;    // 공지 시작일

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;      // 공지 종료일

    // 공지 우선순위 (숫자가 작을수록 높은 우선순위)
    @Column(nullable = false)
    private Integer priority;       

    // 배너 여부
    @Column(nullable = false)
    private Boolean banner;         

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 등록일시

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정일시
}
