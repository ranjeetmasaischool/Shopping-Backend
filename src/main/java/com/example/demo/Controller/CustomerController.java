package com.example.demo.Controller;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dao.AddressDaoImplimentation;
import com.example.demo.Dao.CustomerDaoPattern;
import com.example.demo.Dao.ProductDao;
import com.example.demo.Dao.UserSesionDao;
import com.example.demo.Exception.CatogryException;
import com.example.demo.Exception.LogInException;
import com.example.demo.Exception.ProductException;
import com.example.demo.Model.Address;
import com.example.demo.Model.AdminLogIn;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Product;
import com.example.demo.Model.User;
import com.example.demo.Model.UserSesionControll;

@RestController

public class CustomerController {

	@Autowired
	private CustomerDaoPattern cusDpo;

	@Autowired
	private UserSesionDao usd;

	@Autowired
	private AddressDaoImplimentation addre;

	@Autowired
	private ProductDao produDao;

	// For Registration

	@PostMapping("/registration")
	public ResponseEntity<Customer> registrNewCustomer(
			@org.springframework.web.bind.annotation.RequestBody Customer customer) {

		Customer cus = cusDpo.addCustomer(customer);

		return new ResponseEntity<Customer>(cus, HttpStatus.ACCEPTED);

	}

	// For LogIn

	@PostMapping("/login")
	public ResponseEntity<Customer> logIn(@org.springframework.web.bind.annotation.RequestBody AdminLogIn alogin,
			HttpServletResponse name, @CookieValue(value = "emailId", defaultValue = "Fail") String response)
			throws LogInException {

		if (!response.equals("Fail"))
			throw new LogInException("Already LogIn...");

		if (usd.cheackUserSesion(alogin.getEmail())) {
			return new ResponseEntity<Customer>(cusDpo.findByEmail(alogin.getEmail()), HttpStatus.OK);
		}

		String email = alogin.getEmail();
		String password = alogin.getPassword();

		Customer cus = cusDpo.logIn(email, password);
		Cookie coocke = new Cookie("emailId", URLEncoder.encode(email));
		name.addCookie(coocke);
		UserSesionControll usc = new UserSesionControll(cus.getCustomerId(), email, LocalDateTime.now());
		usd.startSesion(usc);
		return new ResponseEntity<Customer>(cus, HttpStatus.ACCEPTED);
	}

	// For LogOut
	@GetMapping("/logout")
	public ResponseEntity<String> logOut(HttpServletResponse response,
			@CookieValue(value = "emailId", defaultValue = "Fail") String coockie) throws LogInException {

		if (coockie.equals("Fail"))
			throw new LogInException("Already LogOut...");

		Cookie coocke = new Cookie("emailId", URLEncoder.encode("null"));
		coocke.setMaxAge(0);

		response.addCookie(coocke);

		usd.endSesion(coockie);

		return new ResponseEntity<String>("Logout..", HttpStatus.OK);
	}

	// For Add Address..

	@PostMapping("/address")
	public ResponseEntity<Address> addAddress(@RequestBody Address address,
			@CookieValue(value = "emailId", defaultValue = "Fail") String email) throws LogInException {

		if (email.equals("Fail"))
//			return new ResponseEntity<Address>(new Address(), HttpStatus.FAILED_DEPENDENCY);
			throw new LogInException("Please LogIn...");

		return new ResponseEntity<Address>(addre.addAddresh(address, email), HttpStatus.ACCEPTED);

	}

	@PutMapping("/address")
	public ResponseEntity<Address> updateAddress(@RequestBody Address address,
			@CookieValue(value = "emailId", defaultValue = "Fail") String email) throws LogInException {

		if (email.equals("Fail"))
//			return new ResponseEntity<Address>(new Address(), HttpStatus.FAILED_DEPENDENCY);
			throw new LogInException("Please LogIn...");

		return new ResponseEntity<Address>(addre.updateAddresh(address, email), HttpStatus.ACCEPTED);

	}

	@DeleteMapping("/address")
	public ResponseEntity<Address> deleteAddress(@RequestBody Address address,
			@CookieValue(value = "emailId", defaultValue = "Fail") String email) throws LogInException {
		if (email.equals("Fail"))
//			return new ResponseEntity<Address>(new Address(), HttpStatus.FAILED_DEPENDENCY);
			throw new LogInException("Please LogIn...");

		return new ResponseEntity<Address>(addre.removeAddress(email), HttpStatus.ACCEPTED);
	}

	@GetMapping("/address")
	public ResponseEntity<List<Address>> viewAddress(@RequestBody Address address,
			@CookieValue(value = "emailId", defaultValue = "Fail") String email) throws LogInException {
		if (email.equals("Fail"))
//			return new ResponseEntity<List<Address>>(new ArrayList<>(),HttpStatus.FAILED_DEPENDENCY);
			throw new LogInException("Please LogIn...");

		return new ResponseEntity<List<Address>>(addre.viewAllAddress(email), HttpStatus.ACCEPTED);
	}

	// For Product..

	@GetMapping("/product")
	public ResponseEntity<List<Product>> getAllProducts(
			@CookieValue(value = "emailId", defaultValue = "Fail") String response)
			throws LogInException, ProductException {
		if (response.equals("Fail"))
			throw new LogInException("Please LogIn...");
		List<Product> products = produDao.viewAllProduct();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductByProductId(@PathVariable("id") Integer pid,
			@CookieValue(value = "emailId", defaultValue = "Fail") String response)
			throws LogInException, ProductException {
		if (response.equals("Fail"))
			throw new LogInException("Please LogIn...");

		Product product = produDao.viewproductById(pid);

		return new ResponseEntity<Product>(product, HttpStatus.OK);

	}

	@GetMapping("/product/{name}")
	public ResponseEntity<List<Product>> getProductByCatogry(@PathVariable("name") String cName,
			@CookieValue(value = "emailId", defaultValue = "Fail") String response)
			throws LogInException, CatogryException, ProductException {

		if (response.equals("Fail"))
			throw new LogInException("Please LogIn...");
		List<Product> products = produDao.vieeProductByCatogry(response);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping("/test")
	public Customer test(@CookieValue(value = "emailId", defaultValue = "Fail") String email) throws LogInException {
		if (email.equals("Fail"))
			throw new LogInException("Please LogIn...");

		return cusDpo.findByEmail(email);

	}

}
