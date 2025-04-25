package com.testNo2.crm_api.service;

import java.util.List;
import java.util.Optional;

import com.testNo2.crm_api.dto.CustomerSalesRankDTO;
import com.testNo2.crm_api.model.Customer;

public interface CustomerService {
	
	Customer createCustomer(Customer customer);
	List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Long customerId);
    Optional<Customer> updateCustomer(Long customerId, Customer customerDetails);
    boolean deleteCustomer(Long customerId);
    List<CustomerSalesRankDTO> getCustomerSalesRankLastYear();
}
