package com.heechang.hcMan.domain.shipment.repository;

import com.heechang.hcMan.domain.order.entity.OrderRequest;
import com.heechang.hcMan.domain.shipment.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByOrder(OrderRequest order);
}
