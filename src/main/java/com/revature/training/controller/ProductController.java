package com.revature.training.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.training.pms.model.Product;
import com.revature.training.service.ProductService;

@RestController
@RequestMapping("product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
	@Autowired
	ProductService productService;
	
	// 1) localhost:9090/product
	// 2) localhost:9090/product?productName=Aroma
	@GetMapping
	public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) String productName){
		ResponseEntity<List<Product>> responseEntity = null;
		List<Product> products = new ArrayList<Product>();
		if(productName == null) {
			products = productService.getAllProducts();
		}else {
			products = productService.getProductByName(productName);
		}
		if(products.size()==0) {
			responseEntity = new ResponseEntity<List<Product>>(products, HttpStatus.NO_CONTENT);

		}
		else {
			responseEntity = new ResponseEntity<List<Product>>(products, HttpStatus.OK);
			
		}
		System.out.println("All products called");
		return responseEntity;
	}
	
	/*
	 * @GetMapping("/prod") public List<Product> getProduct(){
	 * System.out.println("All products called"); return null; }
	 */

	@GetMapping("{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable("productId")int productId){
		System.out.println("Single product is called "+productId);
		ResponseEntity<Product> responseEntity = null;
		Product product = new Product();
		if(productService.isProductExists(productId)) {
			product = productService.getProductById(productId);
			responseEntity = new ResponseEntity<Product>(product, HttpStatus.OK);
			
		}
		else {
			responseEntity = new ResponseEntity<Product>(product, HttpStatus.OK);

		}
		return responseEntity;
	}
	
	@GetMapping("p/{productName}")
	public ResponseEntity<List<Product>> getProductByName(@PathVariable("productName")String productName){
		ResponseEntity<List<Product>> responseEntity = null;
		List<Product> products = productService.getProductByName(productName);
		if(products.size()==0) {
			responseEntity = new ResponseEntity<List<Product>>(products, HttpStatus.NO_CONTENT);

		}
		else {
			System.out.println("getting all products by product name "+productName);
			responseEntity = new ResponseEntity<List<Product>>(products, HttpStatus.OK);
			
		}
		return responseEntity;
		
	}
	
	@PostMapping
	public ResponseEntity<String> addProduct(@RequestBody Product product) {
		ResponseEntity<String> responseEntity = null;
		int productId = product.getProductId();
		String message = null;
		if(productService.isProductExists(productId)) {
			message = "Product with product id : "+ productId + " already exists";
			responseEntity = new ResponseEntity<String>(message,HttpStatus.OK);
			
		}else {
			productService.addProduct(product);
			message = "Product with product id : "+ productId + " saved successfully";
			responseEntity = new ResponseEntity<String>(message,HttpStatus.OK);
			
		}
		return responseEntity;
	}
	
	@DeleteMapping("{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable("productId")int productId) {
		ResponseEntity<String> responseEntity = null;
		String message = null;
		if(productService.isProductExists(productId)) {
			productService.deleteProduct(productId);
			message = "Product with product id : "+ productId + " deleted successfully";
			responseEntity = new ResponseEntity<String>(message, HttpStatus.OK);
			
		}
		else {
			message = "Product with product id : "+ productId + " doesn't exist";
			responseEntity = new ResponseEntity<String>(message, HttpStatus.NO_CONTENT);

		}
		return responseEntity;
	}
	@PutMapping
	public ResponseEntity<String> updateProduct(@RequestBody Product product) {
		ResponseEntity<String> responseEntity = null;
		int productId = product.getProductId();
		String message = null;
		if(productService.isProductExists(productId)) {
			productService.updateProduct(product);
			message = "Product with product id : "+ productId + " updated successfully";
			responseEntity = new ResponseEntity<String>(message,HttpStatus.OK);
			
		}else {
			message = "Product with product id : "+ productId + " doesn't exist";
			responseEntity = new ResponseEntity<String>(message,HttpStatus.NO_CONTENT);
			
		}
		return responseEntity;
	}
	
	@GetMapping("{minPrice}/{maxPrice}")
	public List<Product> getProductByPrice(@PathVariable int minPrice,@PathVariable  int maxPrice){
		return productService.filterByPrice(minPrice, maxPrice);
	}
	
	
	  @GetMapping("{productName}/{minPrice}/{maxPrice}") 
	  public List<Product> getAlikeProducts(@PathVariable String productName,@PathVariable int minPrice,@PathVariable  int maxPrice){
		  List<Product> products = productService.getProductAlikeNames(productName);
		  return ((ProductController) products).getProductByPrice(minPrice, maxPrice); 
	  }
	 
}
