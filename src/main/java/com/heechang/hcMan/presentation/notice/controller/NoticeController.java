package com.heechang.hcMan.presentation.notice.controller;

import com.heechang.hcMan.domain.notice.application.NoticeService;
import com.heechang.hcMan.domain.notice.dto.CreateNoticeRequestDTO;
import com.heechang.hcMan.domain.notice.dto.NoticeDTO;
import com.heechang.hcMan.domain.notice.dto.UpdateNoticeRequestDTO;
import com.heechang.hcMan.domain.notice.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 전체 조회 및 검색 (파라미터가 있을 경우 검색)
    @GetMapping
    public ResponseEntity<List<NoticeDTO>> getNotices(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Integer priority,
            @RequestParam(required = false) Boolean banner
    ) {
        List<Notice> notices;
        if ((title != null && !title.trim().isEmpty()) ||
            (author != null && !author.trim().isEmpty()) ||
            startDate != null || endDate != null || priority != null || banner != null) {
            notices = noticeService.searchNotices(title, author, startDate, endDate, priority, banner);
        } else {
            notices = noticeService.getAllNotices();
        }
        List<NoticeDTO> dtos = notices.stream()
                .map(NoticeDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable Long id) {
        Notice notice = noticeService.getNotice(id);
        return ResponseEntity.ok(NoticeDTO.fromEntity(notice));
    }

    // 생성
    @PostMapping
    public ResponseEntity<NoticeDTO> createNotice(@RequestBody CreateNoticeRequestDTO dto) {
        Notice notice = noticeService.createNotice(dto);
        return ResponseEntity.ok(NoticeDTO.fromEntity(notice));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<NoticeDTO> updateNotice(@PathVariable Long id, @RequestBody UpdateNoticeRequestDTO dto) {
        Notice updated = noticeService.updateNotice(id, dto);
        return ResponseEntity.ok(NoticeDTO.fromEntity(updated));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }
}
