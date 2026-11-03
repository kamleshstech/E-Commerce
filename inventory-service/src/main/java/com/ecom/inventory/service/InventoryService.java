package com.ecom.inventory.service;

import com.ecom.inventory.dto.InventoryRequest;
import com.ecom.inventory.dto.InventoryResponse;

public interface InventoryService {
	
	void addInventory(InventoryRequest request);
	InventoryResponse checkInventoryInStock(String skuCode);
}
