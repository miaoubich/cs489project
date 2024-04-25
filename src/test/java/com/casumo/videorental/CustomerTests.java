//package com.casumo.videorental;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.casumo.videorental.dto.CustomerRequest;
//import com.casumo.videorental.dto.CustomerResponse;
//import com.casumo.videorental.dto.RentalRequest;
//import com.casumo.videorental.dto.RentalResponse;
//import com.casumo.videorental.exception.CustomerServiceCustomException;
//import com.casumo.videorental.model.Customer;
//import com.casumo.videorental.model.Rental;
//import com.casumo.videorental.repository.CustomerRepository;
//import com.casumo.videorental.service.CustomerService;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@SpringBootTest
//@RequiredArgsConstructor
//@Slf4j
//public class CustomerTests {
//
//	@Autowired
//	CustomerService customerService1;
//	@Mock
//	CustomerService customerService;
//	@Mock
//	CustomerRepository customerRepository;
//
//	private Long customerId1 = 1L;
//	private Long customerId2 = 2L;
//
//	@Test
//	public void createCustomerTest() throws Exception {
//		List<Rental> rentals = rentals();
//		Customer customer = buildCustomer();
//		CustomerRequest customerRequest = buildCustomerRequest();
//		CustomerResponse expectedResult = buildCustomerResponse();
//		rentals.forEach(r -> r.setCustomer(customer));
//
//		// Mocking the behavior of customerRepository.save(customer)
//		when(customerRepository.save(
//				Customer.builder()
//				.firstName("Ali")
//				.lastName("Bouzar")
//				.email("ali@email.net")
//				.rentals(rentals)
//				.build())).thenReturn(customer);
//		
//		CustomerResponse actualResult = customerService1.createCustomer(customerRequest);
//		log.info("actualResult: " + actualResult);
//		
//		// Assert
//		assertEquals(expectedResult.getId(), actualResult.getId());
//		assertEquals(expectedResult.getFirstName(), actualResult.getFirstName());
//		assertEquals(expectedResult.getLastName(), actualResult.getLastName());
//		assertEquals(expectedResult.getEmail(), actualResult.getEmail());
//		assertEquals(expectedResult.getRentals().get(0).getId(), actualResult.getRentals().get(0).getId());
//		assertEquals(expectedResult.getRentals(), actualResult.getRentals());
//		assertEquals(expectedResult.getRentals(), actualResult.getRentals());
//		
//	}
//
//	@Test
//	public void getCustomerByIdTest() throws Exception {
//		// Arrange
//		CustomerResponse expectedResult = buildCustomerResponse();
//		Customer customer = buildCustomer();
//
//		// Act
//		// Mocking the behavior of customerRepository.findById(id)
//		when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
//
//		CustomerResponse actualResult = customerService1.getCustomerById(customerId1);
//		// Verify that the repository method was called with the correct argument
//		verify(customerRepository).findById(customerId1);
//
//		// Assert
//		assertEquals(expectedResult, actualResult);
//		assertEquals(expectedResult.getEmail(), actualResult.getEmail());
//	}
//	
//	@Test
//   	public void getCustomerByIdNotFoundExceptionTest() throws Exception {
//        // Arrange
//        Long customerId = 33L;
//        String expectedMessage = "The Customer with id=" + customerId + " is not registered yet in our database!";
//		String expectedErrorCode = "CUSTOMER_NOT_FOUND";
//		HttpStatus expectedHttpStatusStatus = HttpStatus.NOT_FOUND;
//        
//		// Act
//        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
//
//        CustomerServiceCustomException exception = assertThrows(CustomerServiceCustomException.class,
//                () -> customerService1.getCustomerById(customerId));
//        //Assert
//   		assertEquals(expectedErrorCode, exception.getErrorCode());
//   		assertEquals(expectedMessage, exception.getMessage());
//   		assertEquals(expectedHttpStatusStatus, exception.getStatus());
//   	}
//
//	@Test
//	public void getAllCustomersTest() throws Exception {
//		List<Customer> expectedResult = buildCustomers();
//
//		when(customerRepository.findAll()).thenReturn(expectedResult); // Simulate populated customer list
//
//		// Act
//		List<Customer> actualResult = customerService1.getAllCustomers();
//
//		// Assert
//		assertEquals(expectedResult.size(), actualResult.size());
//		assertEquals(expectedResult.get(0).getId(), actualResult.get(0).getId());
//		assertEquals(expectedResult.get(0).getFirstName(), actualResult.get(0).getFirstName());
//		assertEquals(expectedResult.get(0).getLastName(), actualResult.get(0).getLastName());
//
//		// Verify that customerRepository.findAll() was called once
//		verify(customerRepository, times(1)).findAll();
//	}
//
//	@Test
//	public void deleteCustomerTest() {
//		Customer customer = buildCustomer();
//
//		when(customerRepository.findById(customerId1)).thenReturn(Optional.of(customer));
//
//		// Perform delete operation
//		boolean actualResult = customerService1.deleteCustomer(customerId1);
//		log.info("actualResult: " + actualResult);
//		// Verify that deleteById was called with the correct customerId
//		verify(customerRepository, times(1)).deleteById(customerId1);
//
//		// Assert that the method returns true (customer was deleted)
//		assertTrue(actualResult);
//	}
//
//	@Test
//	    public void deleteCustomerNotFoundTest() {
//			Long customerId1 = 21L;
//	        // Mock behavior of getCustomerById to return null (customer not found)
//	        when(customerRepository.findById(customerId1)).thenReturn(Optional.empty());
//
//	       
//	        boolean actualResult = customerService.deleteCustomer(customerId1);
//
//	        // Verify that deleteById was not called (since customer was not found)
//	        verify(customerRepository, never()).deleteById(customerId1);
//	        log.info("actualResult: " + actualResult);
//	        
//	        // Assert
//	        assertFalse(actualResult);
//	}
//	public CustomerRequest buildCustomerRequest() {
//		return CustomerRequest.builder().id(customerId1).firstName("Ali").lastName("Bouzar").email("ali@email.net")
//				.rentals(rentalRequests()).build();
//	}
//
//	public CustomerResponse buildCustomerResponse() {
//		return CustomerResponse.builder().id(customerId1).firstName("Ali").lastName("Bouzar").email("ali@email.net")
//				.rentals(rentalResponses()).build();
//	}
//
//	List<Rental> rentals() {
//		List<Rental> rentals = new ArrayList<>();
//		String rentalDate1 = "2020, 11, 10";
//		String returnDate1 = "2024, 04, 02";
//		String rentalDate2 = "2023, 05, 12";
//		String returnDate2 = "2025, 06, 23";
//
//		Rental rental1 = Rental.builder().id(1L).rentalDate(rentalDate1).returnDate(returnDate1).build();
//		Rental rental2 = Rental.builder().id(2L).rentalDate(rentalDate2).returnDate(returnDate2).build();
//		rentals.add(rental1);
//		rentals.add(rental2);
//
//		return rentals;
//	}
//
//	public Customer buildCustomer() {
//		return Customer.builder().id(customerId1).firstName("Ali").lastName("Bouzar").email("ali@email.net")
//				.rentals(rentals()).build();
//	}
//
//	List<RentalRequest> rentalRequests() {
//		List<RentalRequest> rentals = new ArrayList<>();
//		String rentalDate1 = "2020, 01, 12";
//		String returnDate1 = "2020, 04, 22";
//		String rentalDate2 = "2020, 04, 12";
//		String returnDate2 = "2020, 05, 23";
//
//		RentalRequest rental1 = RentalRequest.builder().id(1L).rentalDate(rentalDate1).returnDate(returnDate1).build();
//		RentalRequest rental2 = RentalRequest.builder().id(2L).rentalDate(rentalDate2).returnDate(returnDate2).build();
//		rentals.add(rental1);
//		rentals.add(rental2);
//
//		return rentals;
//	}
//
//	List<RentalResponse> rentalResponses() {
//		List<RentalResponse> rentals = new ArrayList<>();
//		String rentalDate1 = "2020, 01, 12";
//		String returnDate1 = "2020, 04, 22";
//		String rentalDate2 = "2020, 04, 12";
//		String returnDate2 = "2020, 05, 23";
//
//		RentalResponse rental1 = RentalResponse.builder().id(1L).rentalDate(rentalDate1).returnDate(returnDate1)
//				.build();
//		RentalResponse rental2 = RentalResponse.builder().id(2L).rentalDate(rentalDate2).returnDate(returnDate2)
//				.build();
//		rentals.add(rental1);
//		rentals.add(rental2);
//
//		return rentals;
//	}
//
//	public List<Customer> buildCustomers() {
//		List<Customer> customers = new ArrayList<>();
//
//		Customer customer1 = Customer.builder().id(customerId1).firstName("Ali").lastName("Bouzar")
//				.email("ali@email.net").rentals(rentals()).build();
//		Customer customer2 = Customer.builder().id(customerId2).firstName("Sam").lastName("Ben").email("sam@email.net")
//				.rentals(rentals()).build();
//		customers.add(customer1);
//		customers.add(customer2);
//
//		return customers;
//	}
//}
