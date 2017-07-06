package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.MultiLanguageProduct;
import com.example.repository.MultiLangProductRepository;

public class ProductServiceImpl implements ProductService {

	@Autowired
	MultiLangProductRepository productRepository;
	
	@Override
	public List<MultiLanguageProduct> findProductByProductName(String productName) {
		return productRepository.findByProductName(productName);
	}

	@Override
	public void saveProduct(MultiLanguageProduct product) {
		productRepository.save(product);
	}

	@Override
	public List<MultiLanguageProduct> getAllProducts() {
		return productRepository.findAll();
	}

}
