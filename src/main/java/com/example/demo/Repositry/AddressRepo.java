package com.example.demo.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Address;

public interface AddressRepo extends JpaRepository<Address, Integer> {

}
