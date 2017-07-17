package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.MultiLanguageProductSegment;
import com.example.repository.MultiLangProductSegmentRepository;

public class SegmentImpla implements SegmentService {

	@Autowired
	MultiLangProductSegmentRepository productSegmentRepository;
	
	@Override
	public List<MultiLanguageProductSegment> getAllSegments() {
		return productSegmentRepository.findAll();
	}


}
