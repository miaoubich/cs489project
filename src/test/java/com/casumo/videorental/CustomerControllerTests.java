package com.casumo.videorental;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.casumo.videorental.dto.CustomerRequest;
import com.casumo.videorental.dto.CustomerResponse;
import com.casumo.videorental.dto.RentalRequest;
import com.casumo.videorental.dto.RentalResponse;
import com.casumo.videorental.model.Customer;
import com.casumo.videorental.model.Rental;
import com.casumo.videorental.repository.CustomerRepository;
import com.casumo.videorental.security.JwtService;
import com.casumo.videorental.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTests {

	@Autowired
	MockMvc mockMvc;
	@Autowired
    private ObjectMapper mapper;
	@Autowired
    private JwtService jwtService;
	@Mock
	CustomerService customerService;
	@Mock
	CustomerRepository customerRepository;

	private Long customerId = 1L;
	private Long customerId1 = 2L;
	
    @Test
    @WithMockUser // Ensure a mock user is used for security context
    public void createCustomerTest() throws Exception {
    	// Arrange
    	CustomerRequest customerRequest = buildCustomerRequest();
    	CustomerResponse customerResponse = buildCustomerResponse();
    	String customerRequestJson = mapper.writeValueAsString(customerRequest);
    	List<Rental> rentals = rentals();
    	// Generate a JWT Token 
        String token = generateMockJwtToken();
    	
    	// Act
    	this.mockMvc.perform(post("/video-rental-store/api/v1/customer")
    			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
    			.content(customerRequestJson).contentType(MediaType.APPLICATION_JSON))
    			.andDo(print())
    			.andExpect(status().isCreated())
    			.andExpect(jsonPath("$.firstName").value(customerResponse.getFirstName()))
    			.andExpect(jsonPath("$.lastName").value(customerResponse.getLastName()))
    			.andExpect(jsonPath("$.email").value(customerResponse.getEmail()))
    			.andExpect(jsonPath("$.rentals", hasSize(rentals.size())))
    			.andReturn();
    }
    
//	@Test
//    public void  getCustomerByIdTest() throws Exception {
//    	Customer customerResponse = buildCustomer();
//    	
//    	MvcResult actualResult = this.mockMvc.perform(get("/video-rental-store/api/v1/customer/{customerId}", customerResponse.getId()))
//    				.andDo(print())
//    				.andExpect(jsonPath("$.id").value(customerResponse.getId()))
//    				.andExpect(jsonPath("$.firstName").value(customerResponse.getFirstName()))
//    				.andExpect(jsonPath("$.lastName").value(customerResponse.getLastName()))
//    				.andExpect(status().isFound())
//    				.andReturn();
//    	
//    	// Extract response content as a String
//        String responseContent = actualResult.getResponse().getContentAsString();
//        
//        // Parse the response content into a Customer object using ObjectMapper
//        Customer actualCustomerResult = mapper.readValue(responseContent, Customer.class);
//        Customer expectedCustomerResult = customerResponse;
//        
//        // Perform assertions on the extracted Customer object
//        assertEquals(expectedCustomerResult.getId(), actualCustomerResult.getId());
//        assertEquals(expectedCustomerResult.getFirstName(), actualCustomerResult.getFirstName());
//        assertEquals(expectedCustomerResult.getLastName(), actualCustomerResult.getLastName());
//        assertEquals(expectedCustomerResult.getRentals().size(), actualCustomerResult.getRentals().size());
//    }
//    
//    @Test
//    public void getAllCustomersTest() throws Exception {
//    	List<Customer> customers = buildCustomers();
//    	
//    	when(customerRepository.findAll()).thenReturn(customers);
//    	
//    	this.mockMvc.perform(get("/video-rental-store/api/v1/customer"))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.length()").value(1));
//    }
//    
//    @Test
//    public void updateCustomerTest() throws Exception {
//    	// Mocking the service response
//        CustomerRequest customerRequest = buildCustomerRequest();
//        List<RentalResponse> rentalResponses = rentalResponses();
//        
//        CustomerResponse updatedCustomerResponse = CustomerResponse.builder()
//                .id(customerId)
//                .firstName(customerRequest.getFirstName().concat(" [Updated]"))
//                .lastName(customerRequest.getLastName().concat(" [Updated]"))
//                .email(customerRequest.getEmail())
//                .rentals(rentalResponses) // Assuming rentals are updated as well
//                .build();
//
//    	when(customerService.updateCustomer(any(), any())).thenReturn(updatedCustomerResponse);
//    	when(customerService.updateCustomer(customerId, customerRequest)).thenReturn(updatedCustomerResponse);
//
//    }
//    
//    @Test
//    public void deleteCustomerTest() throws Exception {
//    	// Verify that the service method was called with the correct customer ID
//    	when(customerService.deleteCustomer(customerId)).thenReturn(true);
//    	
//    	 // Perform DELETE request to delete customer
//    	mockMvc.perform(delete("/video-rental-store/api/v1/customer/{id}", customerId)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }
    
    public CustomerRequest buildCustomerRequest() {
    	return CustomerRequest.builder()
    			.id(customerId)
    			.firstName("Ali")
    			.lastName("Bouzar")
    			.email("ali@email.net")
    			.rentals(Collections.emptyList())
    			.rentals(rentalRequests())
    			.build();
    }

    public CustomerResponse buildCustomerResponse() {
    	return CustomerResponse.builder()
    			.id(customerId)
    			.firstName("Ali")
    			.lastName("Bouzar")
    			.email("ali@email.net")
    			.rentals(rentalResponses())
    			.build();
    }
    
    public Customer buildCustomer() {
    	return Customer.builder()
    			.id(customerId)
    			.firstName("Ali")
    			.lastName("Bouzar")
    			.email("ali@email.net")
    			.rentals(rentals())
    			.build();
    }
    
    List<RentalRequest> rentalRequests(){
    	List<RentalRequest> rentals = new ArrayList<>();
    	String rentalDate1 = "2020, 11, 10";
    	String returnDate1 = "2024, 04, 02";
    	String rentalDate2 = "2023, 05, 12";
    	String returnDate2 = "2025, 06, 23";
    	
    	RentalRequest rental1 = RentalRequest.builder()
    								.id(1L)
					    			.rentalDate(rentalDate1)
					    			.returnDate(returnDate1)
					    			.build();
    	RentalRequest rental2 = RentalRequest.builder()
    								.id(2L)
					    			.rentalDate(rentalDate2)
					    			.returnDate(returnDate2)
					    			.build();
    	rentals.add(rental1);
    	rentals.add(rental2);
    	
    	return rentals;
    }
    
    List<RentalResponse> rentalResponses(){
    	List<RentalResponse> rentals = new ArrayList<>();
    	String rentalDate1 = "2024, 01, 12";
    	String returnDate1 = "2024, 04, 22";
    	String rentalDate2 = "2024, 04, 12";
    	String returnDate2 = "2024, 05, 23";
    	
    	RentalResponse rental1 = RentalResponse.builder()
					    			.id(1L)
					    			.rentalDate(rentalDate1)
					    			.returnDate(returnDate1)
					    			.build();
    	RentalResponse rental2 = RentalResponse.builder()
					    			.id(2L)
					    			.rentalDate(rentalDate2)
					    			.returnDate(returnDate2)
					    			.build();
    	rentals.add(rental1);
    	rentals.add(rental2);
    	
    	return rentals;
    }
    
    public List<Customer> buildCustomers() {
    	List<Customer> customers = new ArrayList<>();
    	
    	Customer customer1 = Customer.builder()
    			.id(customerId)
    			.firstName("Ali")
    			.lastName("Bouzar")
    			.email("ali@email.net")
    			.rentals(rentals())
    			.build();
    	Customer customer2 = Customer.builder()
    			.id(customerId1)
    			.firstName("Sam")
    			.lastName("Ben")
    			.email("sam@email.net")
    			.rentals(rentals())
    			.build();
    	customers.add(customer1);
    	customers.add(customer2);
    	
    	return customers;
    }
    
    List<Rental> rentals(){
    	List<Rental> rentals = new ArrayList<>();
    	String rentalDate1 = "2020, 01, 12";
    	String returnDate1 = "2020, 04, 22";
    	String rentalDate2 = "2020, 04, 12";
    	String returnDate2 = "2020, 05, 23";
    	
    	Rental rental1 = Rental.builder()
					    			.id(1L)
					    			.rentalDate(rentalDate1)
					    			.returnDate(returnDate1)
					    			.build();
    	Rental rental2 = Rental.builder()
					    			.id(2L)
					    			.rentalDate(rentalDate2)
					    			.returnDate(returnDate2)
					    			.build();
    	rentals.add(rental1);
    	rentals.add(rental2);
    	
    	return rentals;
    }
    
 // Generate a JWT token using the JwtService
    private String generateMockJwtToken() {
        String mockUsername = "testUser";
        return jwtService.generateToken(mockUsername);
    }
}
