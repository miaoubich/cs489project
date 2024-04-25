package com.casumo.videorental.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.casumo.videorental.dto.CustomerRequest;
import com.casumo.videorental.dto.CustomerResponse;
import com.casumo.videorental.dto.RentalRequest;
import com.casumo.videorental.dto.RentalResponse;
import com.casumo.videorental.exception.CustomerServiceCustomException;
import com.casumo.videorental.exception.VideoServiceCustomException;
import com.casumo.videorental.model.Customer;
import com.casumo.videorental.model.Rental;
import com.casumo.videorental.model.Video;
import com.casumo.videorental.repository.CustomerRepository;
import com.casumo.videorental.repository.RentalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final RentalRepository rentalRepository;
	

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		log.info("Customer List is being fetch from the database");

		if (customers == null) {
			log.info("No Customer yet being registered in our database!");
			throw new CustomerServiceCustomException("No Customer yet registered in our Database",
					"CUSTOMER_LIST_EMPTY_ERROR", HttpStatus.NO_CONTENT);
		} else {
			log.info("Customer List is populated to display it in the console as follow: " + customers);

			return customers;
		}
	}

	@Override
	public CustomerResponse getCustomerById(Long customerId) {
		log.info("Find the Customer with id=" + customerId + " is on process...");
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerServiceCustomException(
						"The Customer with id=" + customerId + " is not registered yet in our database!",
						"CUSTOMER_NOT_FOUND", HttpStatus.NOT_FOUND));
		
		log.info("The Customer with id=" + customerId + " successfully found!");
		
		return mapper(customer);
	}

	@Override
	public CustomerResponse createCustomer(CustomerRequest customerRequest) {
		Optional<Customer> customerExist = Optional.ofNullable(customerRepository.getCustomerByEmail(customerRequest.getEmail()));
		CustomerResponse customerResponse = null;
		
		if(!customerExist.isPresent()) {
			log.info("Mapping RentalRequest from CustomerRequest Object into Rental object ...");
			List<Rental> rentals = customerRequest.getRentals().stream().map(rentalRequest -> 
				new Rental(rentalRequest.getRentalDate(), rentalRequest.getReturnDate())
			).toList();
			log.info("RentalRequest Object successfully mapped into Rental object!");
			
			Customer customer = Customer.builder()
					.firstName(customerRequest.getFirstName())
					.lastName(customerRequest.getLastName())
					.email(customerRequest.getEmail())
					.build();
			log.info("Customer Object successfully built from CustomerRequest");
			
			Customer actualCustoomer = customerRepository.save(customer);
			
			var savedRentals = new ArrayList<Rental>();
			for(var rental : rentals) {
				rental.setCustomer(actualCustoomer);
				savedRentals.add(rentalRepository.save(rental));
			}
			log.info("Populating rentals with customer_id's since CascadeType didn't work.");
				
			customerResponse = new CustomerResponse();
			BeanUtils.copyProperties(actualCustoomer, customerResponse);
			List<RentalResponse> rentalResponses = savedRentals.stream().map(r -> new RentalResponse(r.getId(), r.getRentalDate(), r.getReturnDate())).toList();
			customerResponse.setRentals(rentalResponses);
			log.info("A customer was successfully created!: " + customerResponse);
		}
		else {
			throw new CustomerServiceCustomException(
					"A Customer with Email: - " + customerRequest.getEmail() + " - already exist!",
					"CUSTOMER_EXIST", HttpStatus.FOUND);
		}
		log.info("Customer " + customerResponse.getFirstName() + " " + customerResponse.getLastName()  + " already exist!");
		return customerResponse;
	}
	
	@Override
	public CustomerResponse updateCustomer(Long customerId, CustomerRequest customerRequest) {
		Customer existCustomer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerServiceCustomException(
						"The Customer with id=" + customerId + " is not registered yet in our database!",
						"CUSTOMER_NOT_FOUND", HttpStatus.NOT_FOUND));
		
		existCustomer.setFirstName(customerRequest.getFirstName());
		existCustomer.setLastName(customerRequest.getLastName());
		existCustomer.setEmail(customerRequest.getEmail());
		existCustomer = customerRepository.save(existCustomer);
		
		if (customerRequest.getRentals() != null && existCustomer.getRentals() != null) {
			existCustomer.getRentals().stream()
			.map(existRental -> {
				RentalRequest rentalRequest = customerRequest.getRentals().stream().filter(re -> re.getId() == existRental.getId()).findFirst().get();
				existRental.setRentalDate(rentalRequest.getRentalDate());
				existRental.setReturnDate(rentalRequest.getReturnDate());
				rentalRepository.save(existRental);
				
				return existRental;
			}).toList();
		}
		return mapper(existCustomer);
	}

	@Override
	public boolean deleteCustomer(Long customerId) {
		if(getCustomerById(customerId) != null) {
			customerRepository.deleteById(customerId);
			return true;
		}
		return false;
	}
	
	private CustomerResponse mapper(Customer customer) {
		CustomerResponse customerResponse = new CustomerResponse();
		BeanUtils.copyProperties(customer, customerResponse);
		
		List<RentalResponse> rentals = customer.getRentals().stream()
				.map(rental -> {
					RentalResponse rentalRes = new RentalResponse();
					BeanUtils.copyProperties(rental, rentalRes);
					return rentalRes;
				}).toList();
		customerResponse.setRentals(rentals);

		return customerResponse;
	}
}
