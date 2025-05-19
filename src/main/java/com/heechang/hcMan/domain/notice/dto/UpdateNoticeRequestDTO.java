package com.heechang.hcMan.domain.notice.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdateNoticeRequestDTO {
    private String title;
    private String content;
    private String author;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer priority;
    private Boolean banner;
}
