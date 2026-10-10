package com.ecom.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecom.product.dto.CategoryDto;
import com.ecom.product.entity.Category;
import com.ecom.product.mapper.CategoryMapper;
import com.ecom.product.repository.CategoryRepository;
import com.ecom.product.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepo;
	private final CategoryMapper categoryMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto dto) {
		Category category = categoryMapper.toEntity(dto);
		Category saveCategory = categoryRepo.save(category);
		
		return categoryMapper.toCategoryDto(saveCategory); 
	}

	@Override
	public CategoryDto updateCategory(Long id, CategoryDto dto) {
		Category category = categoryRepo.findById(id)
			.orElseThrow(() -> new RuntimeException("Category not found with id "+id));
		category.setName(dto.getName());
		category.setDescription(dto.getDescrption());
		category.setParentId(dto.getParentId()); 
		Category updatedCatgory = categoryRepo.save(category); 
		
		return categoryMapper.toCategoryDto(updatedCatgory); 
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepo.deleteById(id);
	}

	@Override
	public CategoryDto getCategory(Long id) {
		Category category = categoryRepo.findById(id) 
			.orElseThrow(()->new RuntimeException("category not found ID "+id));
		
		return categoryMapper.toCategoryDto(category); 
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		
		return categoryRepo.findAll()
					.stream()
					.map(categoryMapper::toCategoryDto)
					.toList();
	}
	
}
