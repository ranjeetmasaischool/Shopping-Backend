package com.example.demo.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.CatogryException;
import com.example.demo.Exception.ProductException;
import com.example.demo.Model.Category;
import com.example.demo.Model.Product;
import com.example.demo.Repositry.CategoryJpa;
import com.example.demo.Repositry.ProductJpa;
@Service
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	private ProductJpa projpa;
	
	@Autowired
	private CategoryJpa catJpa;

	@Override
	public List<Product> viewAllProduct() throws ProductException {
		List<Product> products= projpa.findAll();
		if(products.size()==0) throw new ProductException("No product Avilible...");
		return products;
	}

	@Override
	public Product addProduct(Product product) throws ProductException {
		
		return projpa.save(product);
	}

	@Override
	public Product updateProduct(Product product, Integer pId) throws ProductException {
		Optional<Product> pro=projpa.findById(pId);
		if(pro.isPresent()) {
			Product findedProduct=pro.get();
			product.setProductId(findedProduct.getProductId());
			return projpa.save(product);
			
		}
		throw new ProductException("Product Not Avilable with product Id: "+pId);
	}

	@Override
	public Product viewproductById(Integer id) throws ProductException {
		
		Optional<Product> product= projpa.findById(id);
		if(product.isPresent()) {
			return product.get();
		}
		throw new ProductException("Product Not Avilable With Product Id: "+id);
	}

	@Override
	public List<Product> vieeProductByCatogry(String Cat) throws CatogryException,ProductException {
		Category catagoris= catJpa.findByCatogryName(Cat);
		if(catagoris!=null) {
			List<Product> products=catagoris.getProducts();
			if(products.size()>0) {
				return products;
			}
			throw new ProductException("Product Not Found With Category Name: "+Cat);
		}
		throw new CatogryException("Category Not Found With Category Name: "+Cat);
	}

	@Override
	public Product removeProduct(Integer prodid) throws ProductException {
		Optional<Product> product= projpa.findById(prodid);
		if(product.isPresent()) {
			projpa.deleteById(prodid);
			return product.get();
		}
		throw new ProductException("Product Not Avilable With Product Id: "+prodid);
	}

}
