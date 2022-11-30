package com.example.demo.Dao;

import java.util.List;

import com.example.demo.Model.Customer;

public interface CustomerDaoPattern {
	public Customer addCustomer(Customer customer);
	public Customer findCustomerById(Integer customerId);
	public Customer deleteCustomerById(Integer customerId);
	public List<Customer> viewAllCustomers();
	public Customer logIn(String email,String pass);
	public Customer findByEmail(String email);
	
	
}
