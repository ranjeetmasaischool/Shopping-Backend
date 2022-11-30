package com.example.demo.Dao;

import java.util.List;

import com.example.demo.Exception.CatogryException;
import com.example.demo.Exception.ProductException;
import com.example.demo.Model.Category;
import com.example.demo.Model.Product;

public interface ProductDao {

	public List<Product> viewAllProduct() throws ProductException;
	public Product addProduct(Product product)throws ProductException;
	public Product updateProduct(Product product,Integer pId)throws ProductException;
	public Product viewproductById(Integer id)throws ProductException;
	public List<Product> vieeProductByCatogry(String Cat)throws CatogryException, ProductException;
	public Product removeProduct(Integer prodid)throws ProductException;

}
