package com.heechang.hcMan.domain.payment.repository;

import com.heechang.hcMan.domain.order.entity.OrderRequest;
import com.heechang.hcMan.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrder(OrderRequest order);
    List<Payment> findByOrderId(Long orderId);
}
