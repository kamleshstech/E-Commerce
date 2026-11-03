package com.ecom.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
