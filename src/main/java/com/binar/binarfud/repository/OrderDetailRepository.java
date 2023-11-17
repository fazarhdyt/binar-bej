package com.binar.binarfud.repository;

import com.binar.binarfud.model.OrderDetail;
import com.binar.binarfud.model.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {

    @Modifying
    @Query(value = "DELETE FROM orders_detail od WHERE od.order_id = :orderId", nativeQuery = true)
    void deleteByOrderId(@Param("orderId") String orderId);
}
