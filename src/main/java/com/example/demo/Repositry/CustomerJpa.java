package com.example.demo.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Customer;

public interface CustomerJpa extends JpaRepository<Customer, Integer>{
	
	public Customer findByEmail(String email);

}
