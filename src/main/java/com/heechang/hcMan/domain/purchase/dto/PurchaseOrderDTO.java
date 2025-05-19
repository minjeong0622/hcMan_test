package com.heechang.hcMan.domain.purchase.dto;

import com.heechang.hcMan.domain.purchase.entity.PurchaseOrder;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderDTO {
    private Long id;
    private String purchaseNo;
    private LocalDateTime purchaseDate;
    private Long buyerId;
    private String buyerName;
    private Long supplierId;
    private String supplierName;
    private LocalDate dueDate;
    private String warehouseLocation;
    private String note;
    private String status;
    // 품목 정보
    private String itemCode;
    private String itemName;
    private String unitCode;
    private Integer quantity;
    private String preCancelStatus; // 취소 전 상태

    public static PurchaseOrderDTO fromEntity(PurchaseOrder entity) {
        return PurchaseOrderDTO.builder()
                .id(entity.getId())
                .purchaseNo(entity.getPurchaseNo())
                .purchaseDate(entity.getPurchaseDate())
                .buyerId(entity.getBuyerId())
                .buyerName(entity.getBuyerName())
                .supplierId(entity.getSupplierId())
                .supplierName(entity.getSupplierName())
                .dueDate(entity.getDueDate())
                .warehouseLocation(entity.getWarehouseLocation())
                .note(entity.getNote())
                .status(entity.getStatus().name())
                .itemCode(entity.getItemCode())
                .itemName(entity.getItemName())
                .unitCode(entity.getUnitCode())
                .quantity(entity.getQuantity())
                .preCancelStatus(entity.getPreCancelStatus() != null
                        ? entity.getPreCancelStatus().name()
                        : null)
                .build();
    }
}
