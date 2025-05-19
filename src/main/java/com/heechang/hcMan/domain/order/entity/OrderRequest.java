// OrderRequest.java
package com.heechang.hcMan.domain.order.entity;

import com.heechang.hcMan.domain.user.entity.User;
import com.heechang.hcMan.domain.partner.entity.Partner;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "order_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                  // 발주 ID

    @Column(name = "order_no", unique = true, nullable = false)
    private String orderNo;          // 발주번호 (자동생성)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;              // 발주자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Partner supplier;        // 발주처

    private LocalDateTime orderDate; // 발주일

    private LocalDate dueDate;       // 납기일자

    private String warehouseLocation;// 입고장소

    private String note;             // 비고

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private OrderStatus status;      // 상태

    @Enumerated(EnumType.STRING)
    @Column(name = "pre_cancel_status", length = 20)
    private OrderStatus preCancelStatus; // 취소 전 상태

    // 디테일 (OneToMany)
    @OneToMany(mappedBy = "orderRequest", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    // 발주번호 시퀀스
    private static final AtomicLong ORDER_SEQ = new AtomicLong(1L);

    @PrePersist
    public void prePersist() {
        if (orderDate == null) {
            orderDate = LocalDateTime.now();
        }
        if (status == null) {
            status = OrderStatus.PENDING;
        }
        if (orderNo == null) {
            orderNo = generateOrderNo();
        }
    }

    private String generateOrderNo() {
        long seq = ORDER_SEQ.getAndIncrement();
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return "SCM-" + datePart + "-" + String.format("%06d", seq);
    }

    // 상태 전이 메서드들 (예시)
    public void approve() {
        if (status == OrderStatus.PENDING) {
            status = OrderStatus.APPROVED;
        } else {
            throw new IllegalStateException("현재 상태에서는 발주 승인을 할 수 없습니다.");
        }
    }
    public void ship() {
        if (status == OrderStatus.APPROVED) {
            status = OrderStatus.SHIPPED;
        } else {
            throw new IllegalStateException("현재 상태에서는 출고 처리를 할 수 없습니다.");
        }
    }
    public void deliver() {
        if (status == OrderStatus.SHIPPED) {
            status = OrderStatus.DELIVERED;
        } else {
            throw new IllegalStateException("현재 상태에서는 배송 완료 처리를 할 수 없습니다.");
        }
    }
    public void inspect() {
        if (status == OrderStatus.DELIVERED) {
            status = OrderStatus.INSPECTED;
        } else {
            throw new IllegalStateException("현재 상태에서는 검수를 할 수 없습니다.");
        }
    }
    public void pay() {
        if (status == OrderStatus.INSPECTED) {
            status = OrderStatus.PAID;
        } else {
            throw new IllegalStateException("현재 상태에서는 결제를 할 수 없습니다.");
        }
    }
    public void complete() {
        if (status == OrderStatus.PAID) {
            status = OrderStatus.COMPLETED;
        } else {
            throw new IllegalStateException("현재 상태에서는 최종 완료를 할 수 없습니다.");
        }
    }
    public void cancel() {
        this.preCancelStatus = this.status;
        this.status = OrderStatus.CANCELED;
    }
}
