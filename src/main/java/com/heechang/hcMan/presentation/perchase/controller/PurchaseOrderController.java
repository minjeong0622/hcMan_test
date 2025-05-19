package com.heechang.hcMan.presentation.purchase.controller;

import com.heechang.hcMan.domain.purchase.application.PurchaseOrderService;
import com.heechang.hcMan.domain.purchase.dto.CreatePurchaseOrderRequestDTO;
import com.heechang.hcMan.domain.purchase.dto.PurchaseOrderDTO;
import com.heechang.hcMan.domain.purchase.dto.UpdatePurchaseOrderRequestDTO;
import com.heechang.hcMan.domain.purchase.entity.PurchaseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @GetMapping
    public ResponseEntity<List<PurchaseOrderDTO>> getAllPurchaseOrders(
            @RequestParam(required = false) String buyer,
            @RequestParam(required = false) String supplier,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String orderStatus
    ) {
        List<PurchaseOrder> orders;
        if ((buyer != null && !buyer.trim().isEmpty()) ||
                (supplier != null && !supplier.trim().isEmpty()) ||
                startDate != null || endDate != null ||
                (orderStatus != null && !orderStatus.trim().isEmpty())) {
            orders = purchaseOrderService.searchPurchaseOrders(buyer, supplier, startDate, endDate, orderStatus);
        } else {
            orders = purchaseOrderService.getAllPurchaseOrders();
        }
        List<PurchaseOrderDTO> dtos = orders.stream()
                .map(PurchaseOrderDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(
            @RequestBody CreatePurchaseOrderRequestDTO dto
    ) {
        PurchaseOrder saved = purchaseOrderService.createPurchaseOrder(dto);
        return ResponseEntity.ok(PurchaseOrderDTO.fromEntity(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> getPurchaseOrder(@PathVariable Long id) {
        PurchaseOrder po = purchaseOrderService.getPurchaseOrder(id);
        return ResponseEntity.ok(PurchaseOrderDTO.fromEntity(po));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> updatePurchaseOrder(
            @PathVariable Long id,
            @RequestBody UpdatePurchaseOrderRequestDTO dto
    ) {
        PurchaseOrder updated = purchaseOrderService.updatePurchaseOrder(id, dto);
        return ResponseEntity.ok(PurchaseOrderDTO.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePurchaseOrder(@PathVariable Long id) {
        purchaseOrderService.deletePurchaseOrder(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        try {
            purchaseOrderService.approvePurchaseOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/ship")
    public ResponseEntity<?> ship(@PathVariable Long id) {
        try {
            purchaseOrderService.shipPurchaseOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/deliver")
    public ResponseEntity<?> deliver(@PathVariable Long id) {
        try {
            purchaseOrderService.deliverPurchaseOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/inspect")
    public ResponseEntity<?> inspect(@PathVariable Long id) {
        try {
            purchaseOrderService.inspectPurchaseOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> pay(@PathVariable Long id) {
        try {
            purchaseOrderService.payPurchaseOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) {
        try {
            purchaseOrderService.completePurchaseOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        try {
            purchaseOrderService.cancelPurchaseOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
