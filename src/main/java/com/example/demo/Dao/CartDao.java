package com.example.demo.Dao;

import java.util.List;

import com.example.demo.Exception.ProductException;
import com.example.demo.Model.Cart;
import com.example.demo.Model.Product;

public interface CartDao {
	
	public Cart addProductToCart(Integer produCtId,String customerEmailId,Integer quantity)throws ProductException;
	public Cart removeProductFromCart(Integer productId,String customerEmailId)throws ProductException;
	public Cart updatProduct(String customerEmailId,Integer proId,Integer quantity)throws ProductException;
	public Cart removeAllProduct(String customerEmailId)throws ProductException;
	public List<Product> getAllProduct(String customerEmailId)throws ProductException;

}
