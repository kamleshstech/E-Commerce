package com.ecom.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.order.dto.OrderRequest;
import com.ecom.order.dto.OrderResponse;
import com.ecom.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
	private final OrderService orderService;
	
	@PostMapping
		public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request){
			OrderResponse orderResponse = orderService.placeOrder(request);
			
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(orderResponse);
		}
}
