package com.ecom.inventory.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ecom.inventory.event.OrderPlacedEvent;
import com.ecom.inventory.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderEventConsumer {
	
	private final InventoryService inventoryService;
	
	@KafkaListener(topics = "order-event", groupId = "inventory-group")
	public void consume(OrderPlacedEvent event) {
		inventoryService.updateStock(event);
	}
}
