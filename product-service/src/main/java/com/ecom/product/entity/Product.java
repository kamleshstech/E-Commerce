package com.ecom.product.entity;

import java.time.LocalDateTime;

import org.springframework.lang.Nullable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_tbl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
	@Column(nullable = false)
		private String name;
		private String description;
		private double price;
	@Column(length = 2000)
		private double discountPrice;
		private int quantity;
		private String brand;
		private String imgUrl;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
		private Category category;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;	
	@PrePersist
		public void preCreate() {
			createdAt = LocalDateTime.now();
			updatedAt = LocalDateTime.now();
		}
	@PreUpdate
	    public void preUpdate() {
	    	updatedAt = LocalDateTime.now(); 
	    }
}
