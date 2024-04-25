package com.casumo.videorental.service;

import java.util.List;

import com.casumo.videorental.dto.CustomerRequest;
import com.casumo.videorental.dto.CustomerResponse;
import com.casumo.videorental.model.Customer;

public interface CustomerService {

	List<Customer> getAllCustomers();
	
	CustomerResponse getCustomerById(Long customerId);
	
	CustomerResponse createCustomer(CustomerRequest customerRequest);
	
	CustomerResponse updateCustomer(Long customerId, CustomerRequest customerRequest);
	
	boolean deleteCustomer(Long customerId);
}
