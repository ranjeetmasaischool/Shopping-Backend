package com.example.demo.Controller;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.Cookie;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dao.CustomerDaoPattern;
import com.example.demo.Dao.ProductDao;
import com.example.demo.Dao.UserSesionDao;
import com.example.demo.Exception.CatogryException;
import com.example.demo.Exception.LogInException;
import com.example.demo.Exception.ProductException;
import com.example.demo.Model.AdminLogIn;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Product;
import com.example.demo.Model.UserSesionControll;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CustomerDaoPattern cusDpo;

	@Autowired
	private UserSesionDao usd;

	@Autowired
	private ProductDao produDao;


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

	// Product

	@PostMapping("/product")
	public ResponseEntity<Product> addProduct(@RequestBody Product product,
			@CookieValue(value = "emailId", defaultValue = "Fail") String response)
			throws LogInException, ProductException {
		if (response.equals("Fail"))
			throw new LogInException("Please LogIn...");
		Product savePro = produDao.addProduct(product);

		return new ResponseEntity<Product>(savePro, HttpStatus.ACCEPTED);

	}

	@GetMapping("/product")
	public ResponseEntity<List<Product>> getAllProducts(
			@CookieValue(value = "emailId", defaultValue = "Fail") String response)
			throws LogInException, ProductException {
		if (response.equals("Fail"))
			throw new LogInException("Please LogIn...");
		List<Product> products = produDao.viewAllProduct();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

	}

	@PutMapping("/product/{prid}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable("prid") Integer pid,
			@CookieValue(value = "emailId", defaultValue = "Fail") String response)
			throws LogInException, ProductException {
		if (response.equals("Fail"))
			throw new LogInException("Please LogIn...");

		Product updatedPro = produDao.updateProduct(product, pid);

		return new ResponseEntity<Product>(updatedPro, HttpStatus.ACCEPTED);

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

	@DeleteMapping("/product/{id}")
	public ResponseEntity<Product> deleteProductByProductId(@PathVariable("id") Integer pid,
			@CookieValue(value = "emailId", defaultValue = "Fail") String response)
			throws LogInException, ProductException {
		if (response.equals("Fail"))
			throw new LogInException("Please LogIn...");

		Product product = produDao.removeProduct(pid);

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

	// Customer..

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomers(
			@CookieValue(value = "emailId", defaultValue = "Fail") String response) throws LogInException {
		if (response.equals("Fail"))
			throw new LogInException("Please LogIn...");

		List<Customer> customers = cusDpo.viewAllCustomers();
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);

	}

	@GetMapping("/customers/{cid}")
	public ResponseEntity<Customer> getCustomersById(@PathVariable("cid") Integer cid,
			@CookieValue(value = "emailId", defaultValue = "Fail") String response) throws LogInException {
		if (response.equals("Fail"))
			throw new LogInException("Please LogIn...");

		Customer customers = cusDpo.findCustomerById(cid);
		return new ResponseEntity<Customer>(customers, HttpStatus.OK);

	}

	@DeleteMapping("/customers/{cid}")
	public ResponseEntity<Customer> deleteCustomersById(@PathVariable("cid") Integer cid,
			@CookieValue(value = "emailId", defaultValue = "Fail") String response) throws LogInException {
		if (response.equals("Fail"))
			throw new LogInException("Please LogIn...");
		Customer customers = cusDpo.deleteCustomerById(cid);
		return new ResponseEntity<Customer>(customers, HttpStatus.OK);

	}
	@GetMapping("/customers/{email}")
	public ResponseEntity<Customer> getCustomersByEmail(@PathVariable("email") String email,
			@CookieValue(value = "emailId", defaultValue = "Fail") String response) throws LogInException {
		if (response.equals("Fail"))
			throw new LogInException("Please LogIn...");

		Customer customers = cusDpo.findByEmail(email);
		return new ResponseEntity<Customer>(customers, HttpStatus.OK);

	}

}
