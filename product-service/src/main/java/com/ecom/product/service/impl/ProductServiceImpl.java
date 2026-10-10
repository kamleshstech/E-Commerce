package com.ecom.product.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecom.product.dto.ProductDto;
import com.ecom.product.entity.Category;
import com.ecom.product.entity.Product;
import com.ecom.product.mapper.ProductMapper;
import com.ecom.product.repository.ProductRepository;
import com.ecom.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepository productRepo;
	private final ProductMapper productMapper;
	
	@Override
	public ProductDto createProduct(ProductDto dto) {
		Category category = null;
		if(dto.getCategoryId() != null) {
			category = new Category();
			category.setId(dto.getCategoryId()); 
		}
		Product product = productMapper.toEntity(dto, category);
		Product savedProduct = productRepo.save(product); 
		
		return productMapper.toDto(savedProduct); 
	}

	@Override
	public ProductDto updateProduct(Long id, ProductDto dto) {
		Product p = productRepo.findById(id) 
					.orElseThrow(() -> new RuntimeException("Product is not found"));
		p.setName(dto.getName());
		p.setDescription(dto.getDescription());
		p.setBrand(dto.getBrand());
		p.setPrice(dto.getPrice());
		p.setDiscountPrice(dto.getDiscountPrice());
		if(dto.getCategoryId() != null) {
			Category category = new Category();
			category.setId(dto.getCategoryId()); 
			p.setCategory(category); 
		}
		
		Product updatedProduct = productRepo.save(p);
		
		return productMapper.toDto(updatedProduct); 
	}

	@Override
	public void deleteProduct(Long id) {
		/*Product product = productRepo.findById(id) 
				.orElseThrow(() -> new RuntimeException("product not found to delete"));
		productRepo.delete(product);*/
		
		productRepo.deleteById(id); 
	}

	@Override
	public ProductDto getProductById(Long id) {
		Product product = productRepo.findById(id) 
		.orElseThrow(() -> new RuntimeException("product not found to delete"));
		
		return productMapper.toDto(product); 
	}

	@Override
	public Page<ProductDto> getAllProduct(int page, int size, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		
		Page<Product> productPage = productRepo.findAll(pageable); 
		
		return productPage.map(productMapper::toDto); 
	}

	@Override
	public Page<ProductDto> searchProduct(String keyword, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> searchProductPage = productRepo.searchProduct(keyword, pageable); 
		
		return searchProductPage.map(productMapper::toDto); 
	}

	@Override
	public Page<ProductDto> filterProducts(Long categoryId, Double minPrice, Double maxPrice, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> filterProductsPage = productRepo.advanceFilter(null, categoryId, minPrice, maxPrice, pageable);
		
		return filterProductsPage.map(productMapper::toDto); 
	}

	@Override
	public Page<ProductDto> advancedFilter(String keyword, Long categoryId, Double minPrice, Double maxPrice, int page,
			int size, String sortBy, String sortDir) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> advanceFilterProductPage = productRepo.advanceFilter(keyword, categoryId, minPrice, maxPrice, pageable);
		
		return advanceFilterProductPage.map(productMapper::toDto); 
	}
	
}
