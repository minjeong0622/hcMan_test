package com.heechang.hcMan.domain.purchase.entity;

public enum PurchaseOrderStatus {
    PENDING,    // 대기
    APPROVED,   // 승인됨
    SHIPPED,    // 출고됨
    DELIVERED,  // 배송 완료
    INSPECTED,  // 검수 완료
    PAID,       // 결제 완료
    COMPLETED,  // 최종 완료
    CANCELED    // 취소됨
}
