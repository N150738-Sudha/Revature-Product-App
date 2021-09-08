package com.revature.training.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.training.pms.model.Customer;
import com.revature.training.pms.model.Product;

@RestController
@RequestMapping("customer")
public class CustomerController {
	@GetMapping
	public List<Customer> getCustomers(){
		System.out.println("All customers details are called.....");
		return null;
	}
	
	@GetMapping("{customerId}")
	public <List>Customer getCustomers(@PathVariable("customerId")int custId){
		System.out.println("Customer details are called by using customer id "+custId);
		return null;
	}
	
	@GetMapping("c/{customerName}")
	public List<Product> getProductByName(@PathVariable("customerName")String custName){
		System.out.println("Customer details are called by using customer name "+custName);
		return null;
	}
	
	@PostMapping
	public String addCustomer(@RequestBody Customer customer) {
		System.out.println("add customer called");
		System.out.println(customer);
		return null;
	}
	
	@DeleteMapping("{customerId}")
	public String deleteProduct(@PathVariable("customerId")int custId) {
		System.out.println("Customer with id "+custId+" is getting deleted");
		return null;
	}
	@PutMapping
	public String updateCustomer(@RequestBody Customer customer) {
		System.out.println("Customer is getting updated");
		System.out.println(customer);
		return null;
	}
	
	 

}
