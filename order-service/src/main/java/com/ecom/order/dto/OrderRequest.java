package com.ecom.order.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
	@NotBlank
	private String skuCode;
	@Min(value = 1, message = "quantity must be atleast 1")
	private Integer quantity;
	@NotBlank
	private BigDecimal price;
}
