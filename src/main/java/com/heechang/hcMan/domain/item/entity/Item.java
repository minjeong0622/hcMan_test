package com.heechang.hcMan.domain.item.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // 품목ID (PK)

    @Column(nullable = false)
    private String itemName;  // 품목명

    // 공통코드(UNIT)의 codeId를 저장 (예: "UNIT_EA", "UNIT_KG" 등)
    private String unitCode;

    private String itemCode;
    private String itemCategory;
}
