package com.heechang.hcMan.domain.payment.entity;

import com.heechang.hcMan.domain.order.entity.OrderRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 결제 ID

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderRequest order; // 어떤 주문에 대한 결제인지

    private LocalDateTime paymentDate;
    private BigDecimal amount;          // 결제 금액
    private String paymentStatus;       // 예: COMPLETED, HOLD 등

    @PrePersist
    public void prePersist() {
        if (this.paymentDate == null) {
            this.paymentDate = LocalDateTime.now();
        }
        if (this.paymentStatus == null) {
            this.paymentStatus = "COMPLETED"; // 예시
        }
    }
}