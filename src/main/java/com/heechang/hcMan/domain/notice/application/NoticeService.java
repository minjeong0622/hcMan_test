package com.heechang.hcMan.domain.notice.application;

import com.heechang.hcMan.domain.notice.dto.CreateNoticeRequestDTO;
import com.heechang.hcMan.domain.notice.dto.NoticeDTO;
import com.heechang.hcMan.domain.notice.dto.UpdateNoticeRequestDTO;
import com.heechang.hcMan.domain.notice.entity.Notice;
import com.heechang.hcMan.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.querydsl.core.BooleanBuilder;
import com.heechang.hcMan.domain.notice.entity.QNotice;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 전체 조회
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    // 단건 조회
    public Notice getNotice(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지사항 ID입니다."));
    }

    // 신규 등록
    public Notice createNotice(CreateNoticeRequestDTO dto) {
        Notice notice = Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .priority(dto.getPriority() == null ? 5 : dto.getPriority()) // 기본 우선순위 5
                .banner(dto.getBanner() == null ? false : dto.getBanner())
                .build();
        return noticeRepository.save(notice);
    }

    // 수정
    public Notice updateNotice(Long id, UpdateNoticeRequestDTO dto) {
        Notice notice = getNotice(id);
        if(dto.getTitle() != null) notice.setTitle(dto.getTitle());
        if(dto.getContent() != null) notice.setContent(dto.getContent());
        if(dto.getAuthor() != null) notice.setAuthor(dto.getAuthor());
        if(dto.getStartDate() != null) notice.setStartDate(dto.getStartDate());
        if(dto.getEndDate() != null) notice.setEndDate(dto.getEndDate());
        if(dto.getPriority() != null) notice.setPriority(dto.getPriority());
        if(dto.getBanner() != null) notice.setBanner(dto.getBanner());
        return noticeRepository.save(notice);
    }

    // 삭제
    public void deleteNotice(Long id) {
        Notice notice = getNotice(id);
        noticeRepository.delete(notice);
    }

    // 동적 검색 (QueryDSL 사용)
    public List<Notice> searchNotices(String title, String author, LocalDate startDate, LocalDate endDate, Integer priority, Boolean banner) {
        QNotice qNotice = QNotice.notice;
        BooleanBuilder builder = new BooleanBuilder();

        if (title != null && !title.trim().isEmpty()) {
            builder.and(qNotice.title.containsIgnoreCase(title));
        }
        if (author != null && !author.trim().isEmpty()) {
            builder.and(qNotice.author.containsIgnoreCase(author));
        }
        if (startDate != null) {
            builder.and(qNotice.startDate.goe(startDate));
        }
        if (endDate != null) {
            builder.and(qNotice.endDate.loe(endDate));
        }
        if (priority != null) {
            builder.and(qNotice.priority.eq(priority));
        }
        if (banner != null) {
            builder.and(qNotice.banner.eq(banner));
        }

        return (List<Notice>) noticeRepository.findAll(builder);
    }
    
    // DTO 변환
    public List<NoticeDTO> getAllNoticeDTOs() {
        return getAllNotices().stream()
                .map(NoticeDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
