package com.heechang.hcMan.domain.order.repository;

import com.heechang.hcMan.domain.order.entity.OrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRequestRepository extends JpaRepository<OrderRequest, Long> {

    @Query("""
        SELECT distinct o
        FROM OrderRequest o
        LEFT JOIN FETCH o.buyer
        LEFT JOIN FETCH o.supplier
        LEFT JOIN FETCH o.orderItems oi
        LEFT JOIN FETCH oi.item
        WHERE o.buyer.id = :buyerId
        """)
    List<OrderRequest> findAllByBuyerIdWithItems(@Param("buyerId") Long buyerId);

    @Query("""
        SELECT distinct o
        FROM OrderRequest o
        LEFT JOIN FETCH o.buyer
        LEFT JOIN FETCH o.supplier
        LEFT JOIN FETCH o.orderItems oi
        LEFT JOIN FETCH oi.item
        """)
    List<OrderRequest> findAllWithItems();
}
