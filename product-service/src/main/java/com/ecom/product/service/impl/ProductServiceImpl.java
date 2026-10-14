package com.ecom.product.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	private final String uploadDir = System.getProperty("user.dir")+"/uploads/products";//get the current running project's root directory 
																						//and create folder inside 
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

	@Override
	public ProductDto uploadImage(Long id, MultipartFile file) throws IOException {
	  /*	
		1.check file not empty
		2.check file size
		3.check allowed Types
		4.check allowed extension
		5.get current running project root directory
		6.create folder in root directory
		7.check for folder created or not 
		8.get unique file name 
		9.put file inside uploadDir and get the file path
		10.write coming file into created unique file
		11.get the url of file
		12.save url in db
	*/	
		Product product = productRepo.findById(id)
			.orElseThrow(() -> new RuntimeException("Product not found with this id "+id));
		
		if(file.isEmpty()) {
			throw new RuntimeException("Image file is empty");
		}
		int maxSize = 2*1024*1024;
		if(file.getSize()>maxSize) {
			throw new RuntimeException("file size must be less then 2MB");
		}
		List<String> allowedTypes = List.of("image/jpeg","image/png","image/jpg");
		if(!allowedTypes.contains(file.getContentType())) { 
			throw new RuntimeException("file type not supported!! file type must be [jpeg,png,jpg]"); 
		}
		String originalFilename = file.getOriginalFilename();
		if(originalFilename==null || !originalFilename.contains(".")) { 
			throw new RuntimeException("Invalid file name");
		}
		String ext = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
		List<String> allowedExts = List.of("jpeg","png","jpg");
		if(!allowedExts.contains(ext)) { 
			throw new RuntimeException("Invalid image extension");
		}
		File folder = new File(uploadDir);// This creates the folder 
		if(!folder.exists()) {
			folder.mkdirs();
		}
		String fileName = UUID.randomUUID().toString()+"."+ext; //unique fileName
		Path filePath = Paths.get(uploadDir+fileName); 
		Files.write(filePath, file.getBytes()); //write coming file into created unique file 
		String imageUrl = "/products/images/"+fileName; //get the url of file 
		
		product.setImgUrl(imageUrl);
		Product savedProduct = productRepo.save(product); 
		
		return productMapper.toDto(savedProduct); 
	}
}
