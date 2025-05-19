package com.heechang.hcMan.presentation.order.controller;

import com.heechang.hcMan.JwtUtil;
import com.heechang.hcMan.domain.order.application.OrderService;
import com.heechang.hcMan.domain.order.dto.CreateOrderRequestDTO;
import com.heechang.hcMan.domain.order.dto.OrderRequestDTO;
import com.heechang.hcMan.domain.order.dto.UpdateOrderRequestDTO;
import com.heechang.hcMan.domain.order.entity.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<OrderRequestDTO>> getAllOrders(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String buyer,
            @RequestParam(required = false) String supplier,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String orderStatus
    ) {
        String jwt = token.replace("Bearer ", "");
        Long userId = jwtUtil.extractUserId(jwt);
        String role = jwtUtil.extractRole(jwt);

        List<OrderRequest> orders;
        if ((buyer != null && !buyer.trim().isEmpty())
                || (supplier != null && !supplier.trim().isEmpty())
                || startDate != null
                || endDate != null
                || (orderStatus != null && !orderStatus.trim().isEmpty())) {
            orders = orderService.searchOrders(userId, role, buyer, supplier, startDate, endDate, orderStatus);
        } else {
            orders = orderService.getAllOrders(userId, role);
        }

        List<OrderRequestDTO> dtos = orders.stream()
                .map(OrderRequestDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<OrderRequestDTO> createOrder(
            @RequestBody CreateOrderRequestDTO dto,
            @RequestHeader("Authorization") String token
    ) {
        String jwt = token.replace("Bearer ", "");
        Long userId = jwtUtil.extractUserId(jwt);

        OrderRequest saved = orderService.createOrder(dto);
        return ResponseEntity.ok(OrderRequestDTO.fromEntity(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDTO> getOrder(@PathVariable Long id) {
        OrderRequest order = orderService.getOrder(id);
        return ResponseEntity.ok(OrderRequestDTO.fromEntity(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderRequestDTO> updateOrder(
            @PathVariable Long id,
            @RequestBody UpdateOrderRequestDTO dto
    ) {
        OrderRequest updated = orderService.updateOrder(id, dto);
        return ResponseEntity.ok(OrderRequestDTO.fromEntity(updated));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approveOrder(@PathVariable Long id) {
        try {
            orderService.approveOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/ship")
    public ResponseEntity<?> shipOrder(@PathVariable Long id) {
        try {
            orderService.shipOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/deliver")
    public ResponseEntity<?> deliverOrder(@PathVariable Long id) {
        try {
            orderService.deliverOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/inspect")
    public ResponseEntity<?> inspectOrder(@PathVariable Long id) {
        try {
            orderService.inspectOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payOrder(@PathVariable Long id) {
        try {
            orderService.payOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable Long id) {
        try {
            orderService.completeOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        try {
            orderService.cancelOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
