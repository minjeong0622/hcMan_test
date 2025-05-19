package com.heechang.hcMan.presentation.partner.controller;

import com.heechang.hcMan.domain.partner.application.PartnerService;
import com.heechang.hcMan.domain.partner.entity.Partner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping
    public ResponseEntity<List<Partner>> getAllPartners() {
        return ResponseEntity.ok(partnerService.findAll());
    }

    // 수정 코드: {id}가 숫자인 경우에만 매핑되도록 함
    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<Partner> getPartner(@PathVariable Long id) {
        return ResponseEntity.ok(partnerService.findById(id));
    }

    // 추가: 검색 API
    @GetMapping("/search")
    public ResponseEntity<List<Partner>> searchPartners(@RequestParam String query) {
        List<Partner> partners = partnerService.searchPartners(query);
        return ResponseEntity.ok(partners);
    }

    @PostMapping
    public ResponseEntity<Partner> createPartner(@RequestBody Partner partner) {
        return ResponseEntity.ok(partnerService.createPartner(partner));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id, @RequestBody Partner partner) {
        return ResponseEntity.ok(partnerService.updatePartner(id, partner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.ok().build();
    }
}
