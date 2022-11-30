package com.example.demo.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Customer;
import com.example.demo.Repositry.CustomerJpa;

import io.swagger.v3.oas.annotations.servers.Server;

@Service
public class CustomerImpl implements CustomerDaoPattern {

	@Autowired
	private CustomerJpa cusJpa;
	

	@Override
	public Customer addCustomer(Customer customer) {

		return cusJpa.save(customer);

	}

	@Override
	public Customer findCustomerById(Integer customerId) {
		Optional<Customer> findedCust = cusJpa.findById(customerId);

		return findedCust.get();

	}

	@Override
	public Customer deleteCustomerById(Integer customerId) {
		Optional<Customer> findedCust = cusJpa.findById(customerId);
		cusJpa.deleteById(customerId);
		return findedCust.get();
	}

	@Override
	public List<Customer> viewAllCustomers() {

		return cusJpa.findAll();

	}

	@Override
	public Customer logIn(String email, String pass) {
		Customer cus = cusJpa.findByEmail(email);
		return cus;
	}

	@Override
	public Customer findByEmail(String email) {
		return cusJpa.findByEmail(email);
		
	}

}
