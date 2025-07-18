package com.ecommerse.project.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerse.project.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}