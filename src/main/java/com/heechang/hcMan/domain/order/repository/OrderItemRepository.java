package com.heechang.hcMan.domain.order.repository;

import com.heechang.hcMan.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
