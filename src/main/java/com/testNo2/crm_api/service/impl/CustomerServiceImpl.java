package com.testNo2.crm_api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testNo2.crm_api.dto.CustomerSalesRankDTO;
import com.testNo2.crm_api.model.Customer;
import com.testNo2.crm_api.repository.CustomerRepository;
import com.testNo2.crm_api.service.CustomerService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository repo;
	private final EntityManager entityManager;

	public CustomerServiceImpl(CustomerRepository customerRepository, EntityManager entityManager) {
		this.repo = customerRepository;
		this.entityManager = entityManager;
	}

	@Override
	public Customer createCustomer(Customer customer) {
		return repo.save(customer);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return repo.findAll();
	}

	@Override
	public Optional<Customer> getCustomerById(Long customerId) {
		return repo.findById(customerId);
	}

	@Override
	public Optional<Customer> updateCustomer(Long customerId, Customer customerDetails) {
		Optional<Customer> optionalCustomer = repo.findById(customerId);

		if (optionalCustomer.isPresent()) {

			Customer existingCustomer = optionalCustomer.get();

			if (customerDetails.getFirstname() != null) {
				existingCustomer.setFirstname(customerDetails.getFirstname());
			}
			if (customerDetails.getLastname() != null) {
				existingCustomer.setLastname(customerDetails.getLastname());
			}
			if (customerDetails.getCustomerDate() != null) {
				existingCustomer.setCustomerDate(customerDetails.getCustomerDate());
			}

			if (customerDetails.getIsVIP() != null) {
				existingCustomer.setIsVIP(customerDetails.getIsVIP());
			}
			if (customerDetails.getStatusCode() != null) {
				existingCustomer.setStatusCode(customerDetails.getStatusCode());
			}

			Customer updatedCustomer = repo.save(existingCustomer);
			return Optional.of(updatedCustomer);
		} else {
			return Optional.empty();
		}

	}

	@Override
	public boolean deleteCustomer(Long customerId) {
		if (repo.existsById(customerId)) {
			repo.deleteById(customerId);
			return true;
		}
		return false;
	}

	@Override
	public List<CustomerSalesRankDTO> getCustomerSalesRankLastYear() {

		String sql = """
				WITH RankedSales AS (
				    SELECT
				        CustomerId,
				        SaleDate,
				        SaleAmount,
				        ROW_NUMBER() OVER(PARTITION BY CustomerId ORDER BY SaleAmount DESC, SaleDate DESC) as rn
				    FROM Sales
				    WHERE SaleDate >= DATEADD(year, -1, GETDATE())
				),
				CustomerMaxSaleDetails AS (
				    SELECT
				        CustomerId,
				        SaleDate,
				        SaleAmount
				    FROM RankedSales
				    WHERE rn = 1
				)
				SELECT
				    cmsd.CustomerId,
				    cmsd.SaleDate,
				    cmsd.SaleAmount AS MaxSaleAmount,
				    DENSE_RANK() OVER (ORDER BY cmsd.SaleAmount DESC) AS SalesRank
				FROM CustomerMaxSaleDetails cmsd
				ORDER BY SalesRank ASC, cmsd.SaleAmount DESC;
				       """;

		Query query = entityManager.createNativeQuery(sql, "CustomerSalesRankMapping");

		@SuppressWarnings("unchecked")
		List<CustomerSalesRankDTO> results = query.getResultList();

		return results;
	}

}
