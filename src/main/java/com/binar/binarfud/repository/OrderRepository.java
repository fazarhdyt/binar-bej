package com.binar.binarfud.repository;

import com.binar.binarfud.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByOrderTimeBefore(Date cutoffDate);
}
