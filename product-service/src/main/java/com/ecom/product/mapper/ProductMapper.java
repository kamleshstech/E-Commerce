package com.ecom.product.mapper;

import com.ecom.product.dto.ProductDto;
import com.ecom.product.entity.Category;
import com.ecom.product.entity.Product;

public class ProductMapper {
	
	public ProductDto toDto(Product product) {
		return ProductDto
				.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.discountPrice(product.getDiscountPrice())
				.quantity(product.getQuantity())
				.brand(product.getBrand())
				.imgUrl(product.getImgUrl())
				.categoryId(product.getCategory().getId())
				.build();
	}
	
	public Product toEntity(ProductDto pDto, Category category) {
		return Product
				.builder()
				.name(pDto.getName())
				.description(pDto.getDescription())
				.price(pDto.getPrice())
				.discountPrice(pDto.getDiscountPrice())
				.quantity(pDto.getQuantity())
				.brand(pDto.getBrand())
				.imgUrl(pDto.getImgUrl())
				.category(category)
				.build();
	}
}
