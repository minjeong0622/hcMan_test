package com.heechang.hcMan.domain.order.dto;

import com.heechang.hcMan.domain.order.entity.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {

    private Long id;
    private String orderNo;
    private LocalDateTime orderDate;
    private Long buyerId;
    private String buyerName;
    private Long supplierId;
    private String supplierName;
    private LocalDate dueDate;
    private String warehouseLocation;
    private String note;
    private String status;
    private List<OrderItemDTO> items;

    public static OrderRequestDTO fromEntity(OrderRequest order) {
        return OrderRequestDTO.builder()
                .id(order.getId())
                .orderNo(order.getOrderNo())
                .orderDate(order.getOrderDate())
                .buyerId(order.getBuyer() != null ? order.getBuyer().getId() : null)
                .buyerName(order.getBuyer() != null ? order.getBuyer().getCompanyName() : null)
                .supplierId(order.getSupplier() != null ? order.getSupplier().getId() : null)
                .supplierName(order.getSupplier() != null ? order.getSupplier().getPartnerName() : null)
                .dueDate(order.getDueDate())
                .warehouseLocation(order.getWarehouseLocation())
                .note(order.getNote())
                .status(order.getStatus().name())
                .items(order.getOrderItems().stream()
                        .map(OrderItemDTO::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
