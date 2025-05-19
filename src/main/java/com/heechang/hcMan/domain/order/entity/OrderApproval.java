package com.heechang.hcMan.domain.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_approval")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 승인 ID

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderRequest order; // 어떤 주문에 대한 승인인지

    private Long approvedBy;            // 승인 담당자(발주처) ID
    private LocalDateTime approvedDate; // 승인일

    @PrePersist
    public void prePersist() {
        if (this.approvedDate == null) {
            this.approvedDate = LocalDateTime.now();
        }
    }
}
