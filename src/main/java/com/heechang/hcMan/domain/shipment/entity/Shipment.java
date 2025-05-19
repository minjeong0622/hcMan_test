package com.heechang.hcMan.domain.shipment.entity;

import com.heechang.hcMan.domain.order.entity.OrderRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipment")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 출고 ID

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderRequest order; // 어떤 주문에 대한 출고인지

    private LocalDateTime shippedDate;   // 출고일
    private String trackingNumber;       // 송장번호

    @PrePersist
    public void prePersist() {
        if (this.shippedDate == null) {
            this.shippedDate = LocalDateTime.now();
        }
    }
}
