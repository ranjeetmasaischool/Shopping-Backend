package com.example.demo.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.ProductException;
import com.example.demo.Model.Cart;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Product;
import com.example.demo.Repositry.CartJpa;
import com.example.demo.Repositry.CustomerJpa;
import com.example.demo.Repositry.ProductJpa;

@Service
public class CartDaoImple implements CartDao {

	@Autowired
	private CartJpa cartJpa;

	@Autowired
	private ProductJpa proJpa;

	@Autowired
	private CustomerJpa cusJpa;

	@Override
	public Cart addProductToCart(Integer produCtId, String customerEmailId, Integer quantity) throws ProductException {

		Customer customer = cusJpa.findByEmail(customerEmailId);

		Optional<Product> product = proJpa.findById(produCtId);
		if (product.isPresent()) {
			Product pro = product.get();
			pro.setQuantity(quantity);
			customer.getCart().getProducts().add(pro);
			return cusJpa.save(customer).getCart();

		} else {
			throw new ProductException("Product Not Avileble With The product Id: " + produCtId);
		}

	}

	@Override
	public Cart removeProductFromCart(Integer productId, String customerEmailId) throws ProductException {

		Customer customer = cusJpa.findByEmail(customerEmailId);

		List<Product> products = customer.getCart().getProducts();

		if (products.size() > 0) {

			List<Product> listOfNewProduct = new ArrayList<>();
			for (Product p : products) {
				if (p.getProductId() != productId) {
					listOfNewProduct.add(p);
				}
			}
			customer.getCart().setProducts(listOfNewProduct);
			return cusJpa.save(customer).getCart();

		}

		throw new ProductException("Product Not Avilable With product Id: " + productId);
	}

	@Override
	public Cart updatProduct(String customerEmailId, Integer proId, Integer quantity) throws ProductException {

		Customer customer = cusJpa.findByEmail(customerEmailId);

		List<Product> products = customer.getCart().getProducts();
		if (products.size() > 0) {

			for (Product p : products) {
				if (p.getProductId() == proId) {
					p.setQuantity(quantity + p.getQuantity());
					return cusJpa.save(customer).getCart();
				}
			}
			throw new ProductException("Product Not Avilabel In cart With Product Id: " + proId);

		}

		throw new ProductException("Product Not Avileble In Cart");

	}

	@Override
	public Cart removeAllProduct(String customerEmailId) throws ProductException {
		Customer customer = cusJpa.findByEmail(customerEmailId);
		
		customer.getCart().setProducts(null);
		return cusJpa.save(customer).getCart();
		
	}

	@Override
	public List<Product> getAllProduct(String customerEmailId) throws ProductException {
		Customer customer = cusJpa.findByEmail(customerEmailId);

		List<Product> products = customer.getCart().getProducts();
		if(products.size()>0) {
			return products;
		}
		throw new ProductException("Product Not in Your Cart");
	}

}
