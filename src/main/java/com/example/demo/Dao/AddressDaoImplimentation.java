package com.example.demo.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Address;
import com.example.demo.Model.Customer;
import com.example.demo.Repositry.AddressRepo;
import com.example.demo.Repositry.CustomerJpa;

@Service
public class AddressDaoImplimentation implements AddressDaoPattern {

	@Autowired
	private CustomerJpa cusJpa;

	@Autowired
	private AddressRepo addJpa;

	@Override
	public Address addAddresh(Address addresh, String email) {
		Customer cust = cusJpa.findByEmail(email);
		cust.setAddress(addresh);
		cusJpa.save(cust);

		return cust.getAddress();

	}

	@Override
	public Address updateAddresh(Address address, String email) {
		Customer cust = cusJpa.findByEmail(email);
		cust.setAddress(address);
		cusJpa.save(cust);

		return address;
	}

	@Override
	public Address removeAddress(String email) {
		Customer cust = cusJpa.findByEmail(email);
		Address add = cust.getAddress();
		cust.setAddress(null);

		return add;

	}

	@Override
	public List<Address> viewAllAddress(String email) {
		Customer cust = cusJpa.findByEmail(email);
		List<Address> add = (List<Address>) cust.getAddress();

		return add;
	}

	@Override
	public Address viewAddressById(String email, Integer addressid) {
		Customer cust = cusJpa.findByEmail(email);

		Address add = cust.getAddress();

		return add;
	}

}
