package com.ecom.order.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecom.order.dto.OrderRequest;
import com.ecom.order.dto.OrderResponse;
import com.ecom.order.entity.Order;
import com.ecom.order.mapper.OrderMapper;
import com.ecom.order.repo.OrderRepository;
import com.ecom.order.service.OrderService;
import com.ecom.order.util.OrderStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	private final OrderRepository orderRepo;
	private final OrderMapper orderMapper;
	
	@Override
	public OrderResponse placeOrder(OrderRequest request) {
		Order order = orderMapper.toEntity(request);
		order.setOrderNumber(UUID.randomUUID().toString());
		order.setOrderStatus(OrderStatus.CREATED);
		Order savedOrder = orderRepo.save(order);
		
		return orderMapper.toResponse(savedOrder);  
	}
	
}
