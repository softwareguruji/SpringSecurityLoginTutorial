package com.example.service;

import java.util.List;

import com.example.model.MultiLanguageProduct;


public interface ProductService {
	public List<MultiLanguageProduct> findProductByProductName(String productName);
	public void saveProduct(MultiLanguageProduct product);
	public List<MultiLanguageProduct> getAllProducts();
}
