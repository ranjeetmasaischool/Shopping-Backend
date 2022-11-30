package com.example.demo.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Cart;

public interface CartJpa extends JpaRepository<Cart, Integer>{

}
