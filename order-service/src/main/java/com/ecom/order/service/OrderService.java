package com.ecom.order.service;

import com.ecom.order.dto.OrderRequest;
import com.ecom.order.dto.OrderResponse;

public interface OrderService {
	
	OrderResponse placeOrder(OrderRequest request);
}
