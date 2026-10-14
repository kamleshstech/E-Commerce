package com.ecom.product.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.product.dto.ProductDto;


public interface ProductService {
	
	ProductDto createProduct(ProductDto dto);
	ProductDto updateProduct(Long id, ProductDto dto);
	void deleteProduct(Long id);
	ProductDto uploadImage(Long id, MultipartFile file) throws IOException; 
	ProductDto getProductById(Long id);
	Page<ProductDto> getAllProduct(int page, int size, String sortBy, String sortDir);
	Page<ProductDto> searchProduct(String keyword, int page, int size);
	Page<ProductDto> filterProducts(Long categoryId, Double minPrice, Double maxPrice, int page, int size); 
	Page<ProductDto> advancedFilter(String keyword, Long categoryId, Double minPrice, Double maxPrice, int page, int size, String sortBy, String sortDir);
	
}
