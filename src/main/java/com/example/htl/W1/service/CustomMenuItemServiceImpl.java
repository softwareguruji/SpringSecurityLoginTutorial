package com.example.htl.W1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.htl.W1.repository.CustomMenuItemRepository;

@Service
public class CustomMenuItemServiceImpl implements CustomMenuItemService {

	@Autowired
	CustomMenuItemRepository customMenuItemRepository;
}
