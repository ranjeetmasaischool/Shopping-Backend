package com.example.demo.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Product;

public interface ProductJpa extends JpaRepository<Product, Integer>{

}
