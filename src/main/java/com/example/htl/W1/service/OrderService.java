package com.example.htl.W1.service;

import java.util.List;

import com.example.htl.W1.model.Order;
import com.example.model.User;

public interface OrderService {

	List<Order> findUnOrderedCartItemsByUser(User userObj);
	Order saveNewOrder(Order orderObj);
	
}
