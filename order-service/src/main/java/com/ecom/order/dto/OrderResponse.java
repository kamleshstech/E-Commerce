package com.ecom.order.dto;

import com.ecom.order.util.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
	private String orderNumber;
	private OrderStatus orderStatus;
}
