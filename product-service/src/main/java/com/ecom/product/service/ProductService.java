package com.ecom.product.service;

import org.springframework.data.domain.Page;

import com.ecom.product.dto.ProductDto;

public interface ProductService {
	
	ProductDto createProduct(ProductDto dto);
	ProductDto updateProduct(Long id, ProductDto dto);
	void deleteProduct(Long id);
	ProductDto getProductById(Long id);
	Page<ProductDto> getAllProduct(int page, int size, String sortBy, String sortDir);
	Page<ProductDto> searchProduct(String keyword, int page, int size);
	Page<ProductDto> filterProducts(Long categoryId, Double minPrice, Double maxPrice, int page, int size); 
	Page<ProductDto> advancedFilter(String keyword, Long categoryId, Double minPrice, Double maxPrice, int page, int size, String sortBy, String sortDir);
	
}
