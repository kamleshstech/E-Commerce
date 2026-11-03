package com.ecom.order.mapper;

import org.springframework.stereotype.Component;

import com.ecom.order.dto.OrderRequest;
import com.ecom.order.dto.OrderResponse;
import com.ecom.order.entity.Order;

@Component
public class OrderMapper {
	
	public Order toEntity(OrderRequest request) {
		return Order
				.builder()
				.skuCode(request.getSkuCode())
				.qunatity(request.getQuantity())
				.price(request.getPrice())
				.build();
	}
	
	public OrderResponse toResponse(Order order) {
		return new OrderResponse(order.getOrderNumber(), order.getOrderStatus());
	}
}
