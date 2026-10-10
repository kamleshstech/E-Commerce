package com.ecom.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
			private Long id;
		@NotBlank(message = "prduct name must not be empty")	
			private String name;
			private String description;
		@Positive(message = "price must be positive")
			private double price;
			private double discountPrice;
		@Min(value =0, message = "quantiy must be 0 or more")
			private int quantity;
			private String brand;
			private String imgUrl;
		@NotBlank(message = "category id is required")
			private Long categoryId;
}
