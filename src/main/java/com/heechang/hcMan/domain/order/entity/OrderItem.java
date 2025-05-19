package com.heechang.hcMan.domain.order.entity;

import com.heechang.hcMan.domain.item.entity.Item;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // 발주품목 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderRequest orderRequest; // 어떤 발주에 속한 품목인지 (헤더)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;             // 실제 Item 참조 (itemCode, itemName 등은 Item 테이블에서 가져오거나 Snapshot 가능)

    @Column(name = "item_code")
    private String itemCode;       // 품목코드 (스냅샷 저장용, 선택사항)

    @Column(name = "item_name")
    private String itemName;       // 품목명 (스냅샷 저장용, 선택사항)

    @Column(name = "item_category")
    private String itemCategory;   // 품목구분 (스냅샷 저장용, 선택사항)

    @Column(name = "unit_code")
    private String unitCode;       // 단위 (스냅샷 저장용, 선택사항)

    private Integer orderQuantity; // 발주수량
}
