package com.ecom.product.mapper;

import com.ecom.product.dto.CategoryDto;
import com.ecom.product.entity.Category;

public class CategoryMapper {
	
	public CategoryDto toCategoryDto(Category category) {
		return CategoryDto
				.builder()
				.id(category.getId())
				.name(category.getName())
				.descrption(category.getDescription())
				.parentId(category.getParentId())
				.build();
	}
	
	public Category toEntity(CategoryDto dto) {
		return Category
				.builder()
				.name(dto.getName())
				.description(dto.getDescrption())
				.parentId(dto.getParentId())
				.build();
	}
}
