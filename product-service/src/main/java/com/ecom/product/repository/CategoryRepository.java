package com.ecom.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.product.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	List<Category> findByParentId(Long id);
	boolean existsByName(String name); 
}
