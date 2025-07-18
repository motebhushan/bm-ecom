package com.ecommerse.project.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerse.project.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}