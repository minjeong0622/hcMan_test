package com.heechang.hcMan.domain.order.dto;

import com.heechang.hcMan.domain.order.entity.OrderItem;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    private Long id;
    private Long itemId;
    private String itemCategory;
    private String itemCode;
    private String itemName;
    private String unitCode;
    private Integer orderQuantity;

    public static OrderItemDTO fromEntity(OrderItem entity) {
        return OrderItemDTO.builder()
                .id(entity.getId())
                .itemId(entity.getItem() != null ? entity.getItem().getId() : null)
                .itemCategory(entity.getItemCategory())
                .itemCode(entity.getItemCode())
                .itemName(entity.getItemName())
                .unitCode(entity.getUnitCode())
                .orderQuantity(entity.getOrderQuantity())
                .build();
    }
}
