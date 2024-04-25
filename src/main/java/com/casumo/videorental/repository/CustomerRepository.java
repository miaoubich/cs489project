package com.casumo.videorental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casumo.videorental.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer getCustomerByEmail(String email);

}
