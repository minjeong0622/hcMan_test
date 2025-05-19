package com.heechang.hcMan.presentation.inspection.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.heechang.hcMan.JwtUtil;
import com.heechang.hcMan.domain.inspection.application.InspectionService;
import com.heechang.hcMan.domain.inspection.entity.Inspection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/inspections")
@RequiredArgsConstructor
public class InspectionController {

    private final InspectionService inspectionService;
    private final JwtUtil jwtUtil;

    // 검수 목록 조회
    @GetMapping
    public ResponseEntity<List<Inspection>> getAllInspections(
            @RequestHeader("Authorization") String token
    ) {
        // 토큰 검증 및 사용자 정보 추출 필요 시 jwtUtil 활용 가능
        List<Inspection> inspections = inspectionService.getAllInspections();

        // Optional: JSON 직렬화 테스트 로깅
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            String inspectionsJson = objectMapper.writeValueAsString(inspections);
            log.info("inspectionsJson JSON: {}", inspectionsJson);
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 중 오류 발생", e);
        }

        return ResponseEntity.ok(inspections);
    }

    // 단건 검수 조회
    @GetMapping("/{id}")
    public ResponseEntity<Inspection> getShipment(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        Inspection inspection = inspectionService.getInspection(id);
        return ResponseEntity.ok(inspection);
    }

    // 검수 수정
    @PutMapping("/{id}")
    public ResponseEntity<Inspection> updateInspection(
            @PathVariable Long id,
            @RequestBody Inspection updatedInspection,
            @RequestHeader("Authorization") String token
    ) {
        Inspection inspection = inspectionService.updateInspection(id, updatedInspection);
        return ResponseEntity.ok(inspection);
    }
}
