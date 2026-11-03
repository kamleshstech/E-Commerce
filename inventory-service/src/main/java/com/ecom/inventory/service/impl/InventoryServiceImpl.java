package com.ecom.inventory.service.impl;

import org.springframework.stereotype.Service;

import com.ecom.inventory.dto.InventoryRequest;
import com.ecom.inventory.dto.InventoryResponse;
import com.ecom.inventory.entity.Inventory;
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
	
}
