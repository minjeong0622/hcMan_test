package com.heechang.hcMan.domain.order.application;

import com.heechang.hcMan.domain.user.entity.User;
import com.heechang.hcMan.domain.user.repository.UserRepository;
import com.heechang.hcMan.domain.item.entity.Item;
import com.heechang.hcMan.domain.item.repository.ItemRepository;
import com.heechang.hcMan.domain.order.dto.CreateOrderRequestDTO;
import com.heechang.hcMan.domain.order.dto.OrderItemDTO;
import com.heechang.hcMan.domain.order.dto.UpdateOrderRequestDTO;
import com.heechang.hcMan.domain.order.entity.OrderItem;
import com.heechang.hcMan.domain.order.entity.OrderRequest;
import com.heechang.hcMan.domain.order.repository.OrderItemRepository;
import com.heechang.hcMan.domain.order.repository.OrderRequestRepository;
import com.heechang.hcMan.domain.partner.entity.Partner;
import com.heechang.hcMan.domain.partner.repository.PartnerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRequestRepository orderRequestRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;
    private final ItemRepository itemRepository;

    /**
     * 신규 발주 등록 (헤더 + 디테일)
     */
    public OrderRequest createOrder(CreateOrderRequestDTO dto) {
        // 발주자 조회
        User buyer = userRepository.findById(dto.getBuyerId())
                .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));
        // 발주처 조회
        Partner supplier = partnerRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        OrderRequest orderRequest = OrderRequest.builder()
                .buyer(buyer)
                .supplier(supplier)
                .dueDate(dto.getDueDate())
                .warehouseLocation(dto.getWarehouseLocation())
                .note(dto.getNote())
                .build();

        // 디테일(OrderItem) 설정
        if (dto.getItems() != null) {
            for (OrderItemDTO itemDTO : dto.getItems()) {
                // 신규 등록 시 반드시 itemId가 있어야 함
                if (itemDTO.getItemId() == null) {
                    throw new IllegalArgumentException("ItemId must not be null for item: " + itemDTO.getItemName());
                }
                // 실제 Item 엔티티 찾기
                Item item = itemRepository.findById(itemDTO.getItemId())
                        .orElseThrow(() -> new IllegalArgumentException("Item not found"));
                OrderItem orderItem = OrderItem.builder()
                        .orderRequest(orderRequest)
                        .item(item)
                        .itemCode(item.getItemCode())
                        .itemName(item.getItemName())
                        .itemCategory(item.getItemCategory())
                        .unitCode(item.getUnitCode())
                        .orderQuantity(itemDTO.getOrderQuantity())
                        .build();
                orderRequest.getOrderItems().add(orderItem);
            }
        }

        return orderRequestRepository.save(orderRequest);
    }

    /**
     * 발주 목록 조회 (역할에 따라 필터링)
     */
    @Transactional
    public List<OrderRequest> getAllOrders(Long userId, String role) {
        if ("ROLE_BUYER".equals(role)) {
            return orderRequestRepository.findAllByBuyerIdWithItems(userId);
        } else if ("ROLE_ADMIN".equals(role) || "ROLE_MANAGER".equals(role) || "ROLE_SUPERADMIN".equals(role)) {
            return orderRequestRepository.findAllWithItems();
        }
        throw new AccessDeniedException("권한이 없습니다.");
    }

    /**
     * 단건 발주 조회
     */
    @Transactional
    public OrderRequest getOrder(Long orderId) {
        return orderRequestRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
    }

    /**
     * 발주 수정 (헤더 + 디테일)
     */
    public OrderRequest updateOrder(Long orderId, UpdateOrderRequestDTO dto) {
        OrderRequest order = getOrder(orderId);

        if (dto.getBuyerId() != null) {
            User buyer = userRepository.findById(dto.getBuyerId())
                    .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));
            order.setBuyer(buyer);
        }
        if (dto.getSupplierId() != null) {
            Partner supplier = partnerRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));
            order.setSupplier(supplier);
        }
        if (dto.getDueDate() != null) {
            order.setDueDate(dto.getDueDate());
        }
        if (dto.getWarehouseLocation() != null) {
            order.setWarehouseLocation(dto.getWarehouseLocation());
        }
        if (dto.getNote() != null) {
            order.setNote(dto.getNote());
        }

        // 디테일(주문품목) 업데이트 로직
        if (dto.getItems() != null) {
            List<Long> dtoItemIds = dto.getItems().stream()
                    .filter(itemDTO -> itemDTO.getId() != null)
                    .map(OrderItemDTO::getId)
                    .collect(Collectors.toList());

            order.getOrderItems().removeIf(item -> item.getId() != null && !dtoItemIds.contains(item.getId()));

            for (OrderItemDTO itemDTO : dto.getItems()) {
                if (itemDTO.getId() != null) {
                    OrderItem existingItem = order.getOrderItems().stream()
                            .filter(item -> item.getId().equals(itemDTO.getId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("OrderItem not found: " + itemDTO.getId()));
                    existingItem.setOrderQuantity(itemDTO.getOrderQuantity());
                } else {
                    if (itemDTO.getItemId() == null) {
                        throw new IllegalArgumentException("ItemId must not be null for new OrderItem");
                    }
                    Item item = itemRepository.findById(itemDTO.getItemId())
                            .orElseThrow(() -> new IllegalArgumentException("Item not found"));
                    OrderItem newItem = OrderItem.builder()
                            .orderRequest(order)
                            .item(item)
                            .itemCode(item.getItemCode())
                            .itemName(item.getItemName())
                            .itemCategory(item.getItemCategory())
                            .unitCode(item.getUnitCode())
                            .orderQuantity(itemDTO.getOrderQuantity())
                            .build();
                    order.getOrderItems().add(newItem);
                }
            }
        }

        return orderRequestRepository.save(order);
    }

    // 상태 변경 메서드들
    public void approveOrder(Long orderId) {
        OrderRequest order = getOrder(orderId);
        order.approve();
    }
    public void shipOrder(Long orderId) {
        OrderRequest order = getOrder(orderId);
        order.ship();
    }
    public void deliverOrder(Long orderId) {
        OrderRequest order = getOrder(orderId);
        order.deliver();
    }
    public void inspectOrder(Long orderId) {
        OrderRequest order = getOrder(orderId);
        order.inspect();
    }
    public void payOrder(Long orderId) {
        OrderRequest order = getOrder(orderId);
        order.pay();
    }
    public void completeOrder(Long orderId) {
        OrderRequest order = getOrder(orderId);
        order.complete();
    }
    public void cancelOrder(Long orderId) {
        OrderRequest order = getOrder(orderId);
        order.cancel();
    }

    /**
     * 검색 조건에 따른 주문 조회
     */
    public List<OrderRequest> searchOrders(Long userId,
                                           String role,
                                           String buyer,
                                           String supplier,
                                           LocalDate startDate,
                                           LocalDate endDate,
                                           String orderStatus) {
        List<OrderRequest> orders = getAllOrders(userId, role);

        return orders.stream()
                .filter(order -> {
                    boolean match = true;
                    if (buyer != null && !buyer.trim().isEmpty()) {
                        String buyerVal = buyer.toLowerCase();
                        String orderBuyerId = order.getBuyer() != null ? String.valueOf(order.getBuyer().getId()) : "";
                        String orderBuyerName = (order.getBuyer() != null && order.getBuyer().getCompanyName() != null)
                                ? order.getBuyer().getCompanyName().toLowerCase()
                                : "";
                        if (!(orderBuyerId.contains(buyerVal) || orderBuyerName.contains(buyerVal))) {
                            match = false;
                        }
                    }
                    if (supplier != null && !supplier.trim().isEmpty()) {
                        String supplierVal = supplier.toLowerCase();
                        String orderSupplierId = order.getSupplier() != null ? String.valueOf(order.getSupplier().getId()) : "";
                        String orderSupplierName = (order.getSupplier() != null && order.getSupplier().getPartnerName() != null)
                                ? order.getSupplier().getPartnerName().toLowerCase()
                                : "";
                        if (!(orderSupplierId.contains(supplierVal) || orderSupplierName.contains(supplierVal))) {
                            match = false;
                        }
                    }
                    if (startDate != null && order.getOrderDate().toLocalDate().isBefore(startDate)) {
                        match = false;
                    }
                    if (endDate != null && order.getOrderDate().toLocalDate().isAfter(endDate)) {
                        match = false;
                    }
                    if (orderStatus != null && !orderStatus.trim().isEmpty()) {
                        if (!order.getStatus().name().equals(orderStatus)) {
                            match = false;
                        }
                    }
                    return match;
                })
                .collect(Collectors.toList());
    }
}