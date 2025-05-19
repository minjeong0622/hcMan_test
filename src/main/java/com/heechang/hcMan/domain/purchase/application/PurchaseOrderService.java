package com.heechang.hcMan.domain.purchase.application;

import com.heechang.hcMan.domain.purchase.dto.CreatePurchaseOrderRequestDTO;
import com.heechang.hcMan.domain.purchase.dto.UpdatePurchaseOrderRequestDTO;
import com.heechang.hcMan.domain.purchase.entity.PurchaseOrder;
import com.heechang.hcMan.domain.purchase.entity.PurchaseOrderStatus;
import com.heechang.hcMan.domain.purchase.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    // 전체 조회
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAllWithAnyJoins();
    }

    // 단건 조회
    public PurchaseOrder getPurchaseOrder(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 발주 ID입니다."));
    }

    // 신규 발주 등록
    public PurchaseOrder createPurchaseOrder(CreatePurchaseOrderRequestDTO dto) {
        PurchaseOrder po = PurchaseOrder.builder()
                .buyerId(dto.getBuyerId())
                .buyerName(dto.getBuyerName())
                .supplierId(dto.getSupplierId())
                .supplierName(dto.getSupplierName())
                .dueDate(dto.getDueDate())
                .warehouseLocation(dto.getWarehouseLocation())
                .note(dto.getNote())
                .itemCode(dto.getItemCode())
                .itemName(dto.getItemName())
                .unitCode(dto.getUnitCode())
                .quantity(dto.getQuantity() == null ? 0 : dto.getQuantity())
                .build();
        // persist 전에 purchaseNo가 null이면 직접 생성하여 세팅
        if (po.getPurchaseNo() == null) {
            po.setPurchaseNo(po.generateOrderNo());
        }
        return purchaseOrderRepository.save(po);
    }

    // 발주 수정
    public PurchaseOrder updatePurchaseOrder(Long id, UpdatePurchaseOrderRequestDTO dto) {
        PurchaseOrder po = getPurchaseOrder(id);
        if (dto.getBuyerId() != null) po.setBuyerId(dto.getBuyerId());
        if (dto.getBuyerName() != null) po.setBuyerName(dto.getBuyerName());
        if (dto.getSupplierId() != null) po.setSupplierId(dto.getSupplierId());
        if (dto.getSupplierName() != null) po.setSupplierName(dto.getSupplierName());
        if (dto.getDueDate() != null) po.setDueDate(dto.getDueDate());
        if (dto.getWarehouseLocation() != null) po.setWarehouseLocation(dto.getWarehouseLocation());
        if (dto.getNote() != null) po.setNote(dto.getNote());
        if (dto.getItemCode() != null) po.setItemCode(dto.getItemCode());
        if (dto.getItemName() != null) po.setItemName(dto.getItemName());
        if (dto.getUnitCode() != null) po.setUnitCode(dto.getUnitCode());
        if (dto.getQuantity() != null) po.setQuantity(dto.getQuantity());
        else po.setQuantity(0);
        return purchaseOrderRepository.save(po);
    }

    // 발주 삭제
    public void deletePurchaseOrder(Long id) {
        PurchaseOrder po = getPurchaseOrder(id);
        purchaseOrderRepository.delete(po);
    }

    // 상태 변경 메서드들
    public void approvePurchaseOrder(Long id) {
        PurchaseOrder po = getPurchaseOrder(id);
        po.approve();
        purchaseOrderRepository.save(po);
    }
    public void shipPurchaseOrder(Long id) {
        PurchaseOrder po = getPurchaseOrder(id);
        po.ship();
        purchaseOrderRepository.save(po);
    }
    public void deliverPurchaseOrder(Long id) {
        PurchaseOrder po = getPurchaseOrder(id);
        po.deliver();
        purchaseOrderRepository.save(po);
    }
    public void inspectPurchaseOrder(Long id) {
        PurchaseOrder po = getPurchaseOrder(id);
        po.inspect();
        purchaseOrderRepository.save(po);
    }
    public void payPurchaseOrder(Long id) {
        PurchaseOrder po = getPurchaseOrder(id);
        po.pay();
        purchaseOrderRepository.save(po);
    }
    public void completePurchaseOrder(Long id) {
        PurchaseOrder po = getPurchaseOrder(id);
        po.complete();
        purchaseOrderRepository.save(po);
    }
    public void cancelPurchaseOrder(Long id) {
        PurchaseOrder po = getPurchaseOrder(id);
        po.cancel();
        purchaseOrderRepository.save(po);
    }

    // 간단 검색 (예시)
    public List<PurchaseOrder> searchPurchaseOrders(String buyer, String supplier, LocalDate startDate, LocalDate endDate, String status) {
        List<PurchaseOrder> all = getAllPurchaseOrders();
        return all.stream()
                .filter(po -> {
                    boolean match = true;
                    if (buyer != null && !buyer.trim().isEmpty()) {
                        match &= (po.getBuyerName() != null && po.getBuyerName().contains(buyer))
                                || (po.getBuyerId() != null && po.getBuyerId().toString().contains(buyer));
                    }
                    if (supplier != null && !supplier.trim().isEmpty()) {
                        match &= (po.getSupplierName() != null && po.getSupplierName().contains(supplier))
                                || (po.getSupplierId() != null && po.getSupplierId().toString().contains(supplier));
                    }
                    if (startDate != null) {
                        match &= (po.getPurchaseDate() != null && !po.getPurchaseDate().toLocalDate().isBefore(startDate));
                    }
                    if (endDate != null) {
                        match &= (po.getPurchaseDate() != null && !po.getPurchaseDate().toLocalDate().isAfter(endDate));
                    }
                    if (status != null && !status.trim().isEmpty()) {
                        match &= (po.getStatus().name().equalsIgnoreCase(status));
                    }
                    return match;
                })
                .collect(Collectors.toList());
    }
}
