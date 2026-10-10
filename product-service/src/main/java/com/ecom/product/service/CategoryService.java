package com.ecom.product.service;

import java.util.List;

import com.ecom.product.dto.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto dto);
	CategoryDto updateCategory(Long id, CategoryDto dto);
	void deleteCategory(Long id);
	CategoryDto getCategory(Long id);
	List<CategoryDto> getAllCategories();
}
