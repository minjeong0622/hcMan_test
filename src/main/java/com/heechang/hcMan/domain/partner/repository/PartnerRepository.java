package com.heechang.hcMan.domain.partner.repository;

import com.heechang.hcMan.domain.partner.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    // 거래처명이 검색어(query)를 포함하는 결과를 반환 (대소문자 무시)
    List<Partner> findByPartnerNameContainingIgnoreCase(String partnerName);
}
