package com.ecom.product.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.product.dto.ProductDto;
import com.ecom.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto dto){
		return ResponseEntity
				.ok(productService.createProduct(dto)); 
	}
	@PutMapping("/{id}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto dto){
		return ResponseEntity
					.ok(productService.updateProduct(id, dto)); 
	}
	@DeleteMapping("/{id}") 
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		productService.deleteProduct(id); 
		return ResponseEntity
					.ok("product deleted");
	}
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable Long id){
		return ResponseEntity
					.ok(productService.getProductById(id));
	}
	@GetMapping
	public ResponseEntity<Page<ProductDto>> getAllProduct(@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir
			){
		return ResponseEntity
					.ok(productService.getAllProduct(page, size, sortBy, sortDir)); 
				
	}
	@GetMapping("/search")
	public ResponseEntity<Page<ProductDto>> searchProduct(@RequestParam String keyword, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size){
		return ResponseEntity
				.ok(productService.searchProduct(keyword, page, size));  
	}
	@GetMapping("/filter")
	public ResponseEntity<Page<ProductDto>> filterProduct(@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) Double maxPrice,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size){
		return ResponseEntity
				.ok(productService.filterProducts(categoryId, minPrice, maxPrice, page, size)); 
	}
	@GetMapping("/advanceFilter")
	public ResponseEntity<Page<ProductDto>> advanceFilterProduct(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) Double maxPrice,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size,
			@RequestParam(required = false, defaultValue = "id") String sortBy,
			@RequestParam(required = false, defaultValue = "asc") String sortDir
			){
		return ResponseEntity
					.ok(productService.advancedFilter(keyword, categoryId, minPrice, maxPrice, page, size, sortBy, sortDir)); 
	}
	
}































