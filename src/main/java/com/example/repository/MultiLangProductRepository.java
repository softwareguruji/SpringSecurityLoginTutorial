package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.MultiLanguageProduct;
import java.lang.String;
import java.util.List;

@Repository("multiLangProductRepository")
public interface MultiLangProductRepository extends JpaRepository<MultiLanguageProduct, Long>{
	List<MultiLanguageProduct> findByProductName(String productname);
}
