package com.testNo2.crm_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testNo2.crm_api.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
