package com.testNo2.crm_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testNo2.crm_api.dto.CustomerSalesRankDTO;
import com.testNo2.crm_api.model.Customer;
import com.testNo2.crm_api.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		Customer createdCustomer = customerService.createCustomer(customer);
		return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {

		Optional<Customer> customerOptional = customerService.getCustomerById(id);

		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			return ResponseEntity.ok(customer);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
		Optional<Customer> customerOptional = customerService.updateCustomer(id,customerDetails);

		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			return ResponseEntity.ok(customer);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    	
        if (customerService.deleteCustomer(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/sales-rank/last-year")
    public ResponseEntity<List<CustomerSalesRankDTO>> getCustomerSalesRankLastYear(){
    	List<CustomerSalesRankDTO> salesRanks = customerService.getCustomerSalesRankLastYear();
    	 return ResponseEntity.ok(salesRanks);
    }

}
