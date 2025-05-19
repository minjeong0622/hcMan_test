package com.heechang.hcMan.domain.inspection.repository;

import com.heechang.hcMan.domain.inspection.entity.Inspection;
import com.heechang.hcMan.domain.order.entity.OrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InspectionRepository extends JpaRepository<Inspection, Long> {

    Optional<Inspection> findByOrder(OrderRequest order);
}
