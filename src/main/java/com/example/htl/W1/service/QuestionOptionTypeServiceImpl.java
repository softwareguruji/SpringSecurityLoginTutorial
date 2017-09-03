package com.example.htl.W1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.htl.W1.repository.QuestionOptionTypeRepository;

@Service
public class QuestionOptionTypeServiceImpl implements QuestionOptionTypeService {

	@Autowired
	private QuestionOptionTypeRepository questionOptionTypeRepository;
}
