package com.heechang.hcMan.domain.purchase.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdatePurchaseOrderRequestDTO {
    private Long buyerId;
    private String buyerName;
    private Long supplierId;
    private String supplierName;
    private LocalDate dueDate;
    private String warehouseLocation;
    private String note;
    // 품목 정보
    private String itemCode;
    private String itemName;
    private String unitCode;
    private Integer quantity;
}
