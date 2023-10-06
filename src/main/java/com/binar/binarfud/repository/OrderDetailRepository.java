package com.binar.binarfud.repository;

import com.binar.binarfud.model.OrderDetail;
import com.binar.binarfud.model.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
}
