package com.example.demo.Dao;

import java.util.List;

import com.example.demo.Model.Address;

public interface AddressDaoPattern {
	
	public Address addAddresh(Address addresh,String email);
	public Address updateAddresh(Address address,String email);
	public Address removeAddress(String email);
	public List<Address> viewAllAddress(String email);
	public Address viewAddressById(String email,Integer addressid);
}
