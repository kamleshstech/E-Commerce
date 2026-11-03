package com.ecom.inventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.inventory.dto.InventoryRequest;
import com.ecom.inventory.dto.InventoryResponse;
import com.ecom.inventory.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryService invService;
	
	@PostMapping
	public ResponseEntity<String> addInventory(@RequestBody InventoryRequest request){
		invService.addInventory(request);
		return ResponseEntity.status(HttpStatus.CREATED).body("ok");
	}
	
	@GetMapping("/{skuCode}") 
	public ResponseEntity<InventoryResponse> isInStock(@PathVariable String skuCode){
		InventoryResponse invResp = invService.checkInventoryInStock(skuCode);
		return ResponseEntity
					.ok(invResp);
	} 
}
