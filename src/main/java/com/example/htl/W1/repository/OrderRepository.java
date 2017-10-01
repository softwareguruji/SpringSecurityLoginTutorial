package com.example.htl.W1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.htl.W1.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
