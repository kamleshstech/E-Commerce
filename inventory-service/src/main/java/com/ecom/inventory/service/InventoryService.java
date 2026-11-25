package com.ecom.inventory.service;

import com.ecom.inventory.dto.InventoryRequest;
import com.ecom.inventory.dto.InventoryResponse;
import com.ecom.inventory.event.OrderPlacedEvent;

public interface InventoryService {
	
	void addInventory(InventoryRequest request);
	InventoryResponse checkInventoryInStock(String skuCode);
	void updateStock(OrderPlacedEvent event);
}
