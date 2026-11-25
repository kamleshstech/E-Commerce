package com.ecom.inventory.service.impl;

import org.springframework.stereotype.Service;

import com.ecom.inventory.dto.InventoryRequest;
import com.ecom.inventory.dto.InventoryResponse;
import com.ecom.inventory.entity.Inventory;
import com.ecom.inventory.event.OrderPlacedEvent;
import com.ecom.inventory.exception.InventoryNotFoundException;
import com.ecom.inventory.mapper.InventoryMapper;
import com.ecom.inventory.repo.InventoryRepository;
import com.ecom.inventory.service.InventoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService{

	private final InventoryRepository invRepo; 
	private final InventoryMapper mapper;
	
	@Override
	public void addInventory(InventoryRequest request) {
		Inventory inventory = mapper.toEntity(request); 
		invRepo.save(inventory);
	}

	@Override
	public InventoryResponse checkInventoryInStock(String skuCode) {
		Inventory inventory = invRepo.findBySkuCode(skuCode)
			.orElseThrow(() -> new InventoryNotFoundException(skuCode));  
		return mapper.toResponse(inventory); 
	}

	@Override
	public void updateStock(OrderPlacedEvent event) {
		Inventory inventory = invRepo.findBySkuCode(event.getSkuCode())
			.orElseThrow(() -> new InventoryNotFoundException(event.getSkuCode()));
		
		int availableQuantity = inventory.getQuantity();
		int orderedQuantity = event.getQunatity();
		
		if(orderedQuantity > availableQuantity) {
			throw new RuntimeException("insuficient stock for this skuCode "+event.getSkuCode());
		}
		inventory.setQuantity(availableQuantity-orderedQuantity); 
		invRepo.save(inventory);
	}
}
