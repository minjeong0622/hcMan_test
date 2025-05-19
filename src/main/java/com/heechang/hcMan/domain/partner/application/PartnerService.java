package com.heechang.hcMan.domain.partner.application;

import com.heechang.hcMan.domain.partner.entity.Partner;
import com.heechang.hcMan.domain.partner.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerRepository partnerRepository;

    public List<Partner> findAll() {
        return partnerRepository.findAll();
    }

    public Partner findById(Long id) {
        return partnerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("거래처가 존재하지 않습니다: " + id));
    }

    public Partner createPartner(Partner partner) {
        return partnerRepository.save(partner);
    }

    public Partner updatePartner(Long id, Partner newPartner) {
        Partner partner = findById(id);
        partner.setPartnerName(newPartner.getPartnerName());
        partner.setAddress(newPartner.getAddress());
        partner.setPartnerType(newPartner.getPartnerType());
        return partnerRepository.save(partner);
    }

    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }

    // 추가: 검색어(query)를 통한 거래처 검색
    public List<Partner> searchPartners(String query) {
        if (query != null && query.matches("\\d+")) { // query가 숫자면
            Long id = Long.valueOf(query);
            return partnerRepository.findById(id)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        }
        // 그렇지 않으면 기존 이름 검색 로직 실행
        return partnerRepository.findByPartnerNameContainingIgnoreCase(query);
    }
}
