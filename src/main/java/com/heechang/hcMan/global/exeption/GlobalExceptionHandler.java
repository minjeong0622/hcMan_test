package com.heechang.hcMan.global.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Supplier(거래처) 미존재 시 발생하는 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();

        if (ex.getMessage().contains("Supplier not found")) {
            // 여기서 원하는 사용자 친화적 메시지로 교체
            response.put("message", "거래처(공급업체) ID가 존재하지 않습니다.");
        } else {
            // 그 외 IllegalArgumentException이면 그대로 메시지 사용
            response.put("message", ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
