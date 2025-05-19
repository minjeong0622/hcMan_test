package com.heechang.hcMan.domain.notice.repository;

import com.heechang.hcMan.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NoticeRepository extends JpaRepository<Notice, Long>, QuerydslPredicateExecutor<Notice> {
    // QueryDSL을 사용하여 다양한 검색 조건을 쉽게 처리할 수 있습니다.
}
