package com.codewithkz.demokz.modules.order.repository;

import com.codewithkz.demokz.modules.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
