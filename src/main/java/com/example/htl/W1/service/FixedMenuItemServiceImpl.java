package com.example.htl.W1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.htl.W1.model.FixedMenuItems;
import com.example.htl.W1.repository.FixedMenuItemRepository;

@Service
public class FixedMenuItemServiceImpl implements FixedMenuItemService {

	@Autowired
	FixedMenuItemRepository fixedMenuItemRepository;

	@Override
	public FixedMenuItems save(FixedMenuItems fixedmenuItem) {
		return fixedMenuItemRepository.save(fixedmenuItem);
	}
	
}
