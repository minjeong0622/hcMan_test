package com.heechang.hcMan.presentation.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.heechang.hcMan.JwtUtil;
import com.heechang.hcMan.domain.payment.application.PaymentService;
import com.heechang.hcMan.domain.payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final JwtUtil jwtUtil;

    // 결제 목록 조회 (주문 ID 검색 조건을 문자열로 받아 처리)
    @GetMapping
    public ResponseEntity<List<Payment>> getPayments(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "orderId", required = false) String orderIdStr
    ) {
        List<Payment> payments;
        if (orderIdStr != null && !orderIdStr.trim().isEmpty()) {
            try {
                Long orderId = Long.parseLong(orderIdStr.trim());
                payments = paymentService.getPayments(orderId);
            } catch (NumberFormatException ex) {
                // 주문 ID가 숫자로 변환 불가능하면 빈 결과 또는 예외 처리
                log.error("주문 ID 변환 실패: {}", orderIdStr, ex);
                payments = List.of();
            }
        } else {
            payments = paymentService.getAllPayments();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            String paymentsJson = objectMapper.writeValueAsString(payments);
            log.info("paymentsJson JSON: {}", paymentsJson);
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 중 오류 발생", e);
        }

        return ResponseEntity.ok(payments);
    }

    // 단건 결제 조회
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        Payment payment = paymentService.getPayment(id);
        return ResponseEntity.ok(payment);
    }

    // 결제 수정
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long id,
            @RequestBody Payment updatedPayment,
            @RequestHeader("Authorization") String token
    ) {
        Payment payment = paymentService.updatePayment(id, updatedPayment);
        return ResponseEntity.ok(payment);
    }
}