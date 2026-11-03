package com.ecom.inventory.mapper;

import com.ecom.inventory.dto.InventoryRequest;
import com.ecom.inventory.dto.InventoryResponse;
import com.ecom.inventory.entity.Inventory;

public class InventoryMapper {
	public Inventory toEntity(InventoryRequest request) {
		return Inventory
				.builder()
				.skuCode(request.getSkuCode())
				.quantity(request.getQuantity())
				.build();
	}
	
	public InventoryResponse toResponse(Inventory inventory) {
		return new InventoryResponse(
				inventory.getSkuCode(),
				inventory.getQuantity()>0,
				inventory.getQuantity()
				);
	}	
}
