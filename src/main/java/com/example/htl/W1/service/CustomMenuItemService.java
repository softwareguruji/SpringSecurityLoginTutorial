package com.example.htl.W1.service;

import com.example.htl.W1.model.CustomMenuItem;
import com.example.htl.W1.model.Menu;

public interface CustomMenuItemService {

	CustomMenuItem save(CustomMenuItem customMenuItemObj);
	void delete(CustomMenuItem customMenuItemObj);
	CustomMenuItem getByMenuItem(Menu menuObj);
}
