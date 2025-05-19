package com.heechang.hcMan.domain.inspection.entity;

import com.heechang.hcMan.domain.order.entity.OrderRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inspection")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 검수 ID

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderRequest order;  // 어떤 주문에 대한 검수인지

    private Long inspectedBy;           // 검수 담당자 ID
    private LocalDateTime inspectionDate;
    private String inspectionResult;    // 예: 정상/불량 등

    @PrePersist
    public void prePersist() {
        if (this.inspectionDate == null) {
            this.inspectionDate = LocalDateTime.now();
        }
    }
}
