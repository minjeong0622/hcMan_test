package com.heechang.hcMan.domain.shipment.application;

import com.heechang.hcMan.domain.shipment.entity.Shipment;
import com.heechang.hcMan.domain.shipment.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    // 출고 목록 조회
    @Transactional(readOnly = true)
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    // 단건 출고 조회
    @Transactional(readOnly = true)
    public Shipment getShipment(Long shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found"));
    }

    // 출고 수정 (예: trackingNumber, shippedDate 업데이트)
    public Shipment updateShipment(Long shipmentId, Shipment updatedData) {
        Shipment shipment = getShipment(shipmentId);
        // 수정 가능한 필드를 업데이트합니다.
        if (updatedData.getTrackingNumber() != null) {
            shipment.setTrackingNumber(updatedData.getTrackingNumber());
        }
        if (updatedData.getShippedDate() != null) {
            shipment.setShippedDate(updatedData.getShippedDate());
        }
        return shipmentRepository.save(shipment);
    }
}
