package com.ecom.inventory.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.inventory.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
	 
	public Optional<Inventory> findBySkuCode(String skuCode);
}
