package com.heechang.hcMan.domain.notice.dto;

import com.heechang.hcMan.domain.notice.entity.Notice;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer priority;
    private Boolean banner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NoticeDTO fromEntity(Notice entity) {
        return NoticeDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .author(entity.getAuthor())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priority(entity.getPriority())
                .banner(entity.getBanner())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
