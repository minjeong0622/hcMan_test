package com.heechang.hcMan.presentation.shipment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.heechang.hcMan.JwtUtil;
import com.heechang.hcMan.domain.shipment.application.ShipmentService;
import com.heechang.hcMan.domain.shipment.entity.Shipment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final JwtUtil jwtUtil; // JWT 토큰 처리를 위한 유틸 (필요한 경우)

    // 출고 목록 조회
    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments(
            @RequestHeader("Authorization") String token
    ) {
        // 토큰 검증 및 사용자 정보 추출 필요 시 jwtUtil 활용 가능
        List<Shipment> shipments = shipmentService.getAllShipments();

        // Optional: JSON 직렬화 테스트 로깅
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            String shipmentsJson = objectMapper.writeValueAsString(shipments);
            log.info("Shipments JSON: {}", shipmentsJson);
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 중 오류 발생", e);
        }

        return ResponseEntity.ok(shipments);
    }

    // 단건 출고 조회
    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipment(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        Shipment shipment = shipmentService.getShipment(id);
        return ResponseEntity.ok(shipment);
    }

    // 출고 수정
    @PutMapping("/{id}")
    public ResponseEntity<Shipment> updateShipment(
            @PathVariable Long id,
            @RequestBody Shipment updatedShipment,
            @RequestHeader("Authorization") String token
    ) {
        Shipment shipment = shipmentService.updateShipment(id, updatedShipment);
        return ResponseEntity.ok(shipment);
    }
}
