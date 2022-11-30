package com.example.demo.Repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Category;

public interface CategoryJpa extends JpaRepository<Category, Integer> {
	
	public Category findByCatogryName(String catName);
	 

}
