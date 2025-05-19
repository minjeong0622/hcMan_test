package com.heechang.hcMan.domain.purchase.repository;

import com.heechang.hcMan.domain.purchase.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    @Query("SELECT p FROM PurchaseOrder p")
    List<PurchaseOrder> findAllWithAnyJoins();
}
