package com.heechang.hcMan.domain.order.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateOrderRequestDTO {
    private Long buyerId;
    private Long supplierId;
    private LocalDate dueDate;
    private String warehouseLocation;
    private String note;
    private List<OrderItemDTO> items;
}
