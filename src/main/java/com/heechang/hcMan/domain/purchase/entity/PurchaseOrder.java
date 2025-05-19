package com.heechang.hcMan.domain.purchase.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "purchase_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // 발주 ID (PK)

    // 수정된 부분: 실제 DB 컬럼명과 일치하도록 변경 ("purchase_no")
    @Column(name = "purchase_no", unique = true, nullable = false)
    private String purchaseNo;         // 발주번호 (자동생성)

    private Long buyerId;             // 발주자 ID
    private String buyerName;         // 발주자 이름

    private Long supplierId;          // 발주처 ID
    private String supplierName;      // 발주처 이름

    private LocalDateTime purchaseDate; // 발주일
    private LocalDate dueDate;        // 납기일자
    private String warehouseLocation; // 입고장소
    private String note;              // 비고

    // 품목 정보 (헤더와 디테일이 하나의 테이블에 통합됨)
    private String itemCode;          // 품목코드
    private String itemName;          // 품목명
    private String unitCode;          // 단위
    private Integer quantity;         // 수량

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private PurchaseOrderStatus status;  // 상태

    @Enumerated(EnumType.STRING)
    @Column(name = "pre_cancel_status", length = 20)
    private PurchaseOrderStatus preCancelStatus; // 취소 전 상태

    // 발주번호 시퀀스 (단순 예시)
    private static final AtomicLong PURCHASE_SEQ = new AtomicLong(1L);

    @PrePersist
    public void prePersist() {
        if (purchaseDate == null) {
            purchaseDate = LocalDateTime.now();
        }
        if (status == null) {
            status = PurchaseOrderStatus.PENDING;
        }
        if (purchaseNo == null) {
            purchaseNo = generateOrderNo();
        }
    }

    public String generateOrderNo() {
        long seq = PURCHASE_SEQ.getAndIncrement();
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return "PUR-" + datePart + "-" + String.format("%06d", seq);
    }

    // 상태 전이 메서드들 (예시)
    public void approve() {
        if (status == PurchaseOrderStatus.PENDING) {
            status = PurchaseOrderStatus.APPROVED;
        } else {
            throw new IllegalStateException("현재 상태에서는 발주 승인을 할 수 없습니다.");
        }
    }
    public void ship() {
        if (status == PurchaseOrderStatus.APPROVED) {
            status = PurchaseOrderStatus.SHIPPED;
        } else {
            throw new IllegalStateException("현재 상태에서는 출고 처리를 할 수 없습니다.");
        }
    }
    public void deliver() {
        if (status == PurchaseOrderStatus.SHIPPED) {
            status = PurchaseOrderStatus.DELIVERED;
        } else {
            throw new IllegalStateException("현재 상태에서는 배송 완료 처리를 할 수 없습니다.");
        }
    }
    public void inspect() {
        if (status == PurchaseOrderStatus.DELIVERED) {
            status = PurchaseOrderStatus.INSPECTED;
        } else {
            throw new IllegalStateException("현재 상태에서는 검수를 할 수 없습니다.");
        }
    }
    public void pay() {
        if (status == PurchaseOrderStatus.INSPECTED) {
            status = PurchaseOrderStatus.PAID;
        } else {
            throw new IllegalStateException("현재 상태에서는 결제를 할 수 없습니다.");
        }
    }
    public void complete() {
        if (status == PurchaseOrderStatus.PAID) {
            status = PurchaseOrderStatus.COMPLETED;
        } else {
            throw new IllegalStateException("현재 상태에서는 최종 완료를 할 수 없습니다.");
        }
    }
    public void cancel() {
        this.preCancelStatus = this.status;
        this.status = PurchaseOrderStatus.CANCELED;
    }
}
