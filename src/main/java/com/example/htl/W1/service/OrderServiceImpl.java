package com.example.htl.W1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.htl.W1.model.Order;
import com.example.htl.W1.model.OrderStatus;
import com.example.htl.W1.repository.OrderRepository;
import com.example.model.User;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	OrderStatusService orderStatusService;

	
	@Override
	public List<Order> findUnOrderedCartItemsByUser(User userObj) {
		OrderStatus unOrderderedOrder = orderStatusService.getById(1L);
		return orderRepository.findByUserAndOrderStatus(userObj, unOrderderedOrder);
	}


	@Override
	public Order saveNewOrder(Order orderObj) {
		OrderStatus unOrderderedOrder = orderStatusService.getById(1L);
		orderObj.setOrderStatus(unOrderderedOrder);
		return orderRepository.save(orderObj);
	}
	
}
