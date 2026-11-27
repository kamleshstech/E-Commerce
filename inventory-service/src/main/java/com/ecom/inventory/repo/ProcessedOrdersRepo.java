package com.ecom.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.inventory.entity.ProcessedOrder;

public interface ProcessedOrdersRepo extends JpaRepository<ProcessedOrder, Long>{
	
	boolean existByOrderId(String orderId); 
}
