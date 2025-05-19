package com.heechang.hcMan.domain.order.repository;

import com.heechang.hcMan.domain.order.entity.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {
}
