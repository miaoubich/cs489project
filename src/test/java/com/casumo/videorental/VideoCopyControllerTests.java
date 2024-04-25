//package com.casumo.videorental;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.casumo.videorental.dto.RentalRequest;
//import com.casumo.videorental.dto.RentalResponse;
//import com.casumo.videorental.dto.VideoCopyRequest;
//import com.casumo.videorental.dto.VideoCopyResponse;
//import com.casumo.videorental.exception.VideoCopyServiceCustomException;
//import com.casumo.videorental.model.Customer;
//import com.casumo.videorental.model.Rental;
//import com.casumo.videorental.model.VideoCopy;
//import com.casumo.videorental.repository.VideoCopyRepository;
//import com.casumo.videorental.service.VideoCopyService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import lombok.RequiredArgsConstructor;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@RequiredArgsConstructor
//class VideoCopyControllerTests {
//
//	@Autowired
//	VideoCopyService videoCopyService1;
//	@Autowired
//	MockMvc mockMvc;
//	@Autowired
//    private ObjectMapper mapper;
//	@Mock
//	VideoCopyService videoCopyService;
//	@Mock
//	VideoCopyRepository videoCopyRepository;
//
//	private Long customerId = 1L;
//	private Long customerId1 = 2L;
//	
//    @Test
//    public void createVideoCopyTest() throws Exception {
//    	// Arrange
//    	VideoCopyRequest videoCopyRequest = buildVideoCopyRequest();
//    	VideoCopyResponse videoCopyResponse = buildVideoCopyResponse();
//    	String videoCopyRequestJson = mapper.writeValueAsString(videoCopyRequest);
//    	
//    	this.mockMvc.perform(post("/video-rental-store/api/v1/videocopy").content(videoCopyRequestJson).contentType(MediaType.APPLICATION_JSON))
//    			.andDo(print())
//    			.andExpect(status().isCreated())
//    			.andExpect(jsonPath("$.id").exists())
//    			.andExpect(jsonPath("$.available").value(videoCopyResponse.getAvailable()))
//    			.andExpect(jsonPath("$.rentals[0]").value(videoCopyResponse.getRentals().get(0)))
//    			.andReturn();
//    }
//
//	@Test
//    public void  getVideoCopyByIdTest() throws Exception {
//		VideoCopyResponse videoCopyResponse = VideoCopyResponse.builder()
//							.id(1L)
//							.available("True")
//							.rentals(rentalResponses())
//							.build();
//		
//		when(videoCopyService.getVideoCopyById(1L)).thenReturn(videoCopyResponse);
//    	
////    	MvcResult actualResult = 
//    			this.mockMvc.perform(get("/video-rental-store/api/v1/videocopy/{videoCopyId}", videoCopyResponse.getId()))
//    				.andDo(print())
//    				.andExpect(jsonPath("$.id").exists())
//    				.andExpect(jsonPath("$.available").value(videoCopyResponse.getAvailable()))
//    				.andExpect(jsonPath("$.rentals").value(videoCopyResponse.getRentals()))
//    				.andExpect(status().isFound())
//    				.andReturn();
//    	
//    }
//    
//    @Test
//    public void getAllVideoCopiesTest() throws Exception {
//    	List<VideoCopy> videoCopies = new ArrayList<>(Arrays.asList(
//                new VideoCopy(1L, "True"),
//                new VideoCopy(2L, "False"),
//                new VideoCopy(3L, "True")));
// 
//    	when(videoCopyRepository.findAll()).thenReturn(videoCopies);
//    	
//    	this.mockMvc.perform(get("/video-rental-store/api/v1/videocopy"))
//				.andDo(print())
//				.andExpect(status().isOk());
////				.andExpect(jsonPath("$.length()").value(3));
//    }
//    
//    @Test
//    public void updateVideoCopyTest() throws Exception {
//    	// Mocking the service response
//        VideoCopyRequest videoCopyRequest = VideoCopyRequest.builder()
//        		.id(1L)
//        		.available("True")
//        		.rentals(Collections.emptyList())
//        		.build();
//        List<RentalResponse> rentalResponses = rentalResponses();
//        
//        VideoCopyResponse updatedVideoCopyResponseResponse = VideoCopyResponse.builder()
//                .id(1L)
//                .available("True")
//                .rentals(rentalResponses) // Assuming rentals are updated as well
//                .build();
//
//    	when(videoCopyService.updateVideoCopy(any(), any())).thenReturn(updatedVideoCopyResponseResponse);
//    	when(videoCopyService.updateVideoCopy(customerId, videoCopyRequest)).thenReturn(updatedVideoCopyResponseResponse);
//
//    	mockMvc.perform(put("/video-rental-store/api/v1/videocopy/{id}", videoCopyRequest.getId())
//    		.contentType(MediaType.APPLICATION_JSON)
//    		.content(mapper.writeValueAsString(videoCopyRequest)))
//            .andExpect(status().isOk())
//            .andDo(print())
//            .andExpect(jsonPath("$.{id}").exists())
//            .andExpect(jsonPath("$.{available}").value(updatedVideoCopyResponseResponse.getAvailable()));
//    }
//    
//    @Test
//    public void deleteVideoCopyTest() throws Exception {
//    	// Verify that the service method was called with the correct VideoCopy ID
//    	when(videoCopyService.deleteVideoCopy(customerId)).thenReturn(true);
//    	
//    	 // Perform DELETE request to delete a VideoCopy
//    	mockMvc.perform(delete("/video-rental-store/api/v1/videocopy/{id}", customerId)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }
//    
//    List<RentalRequest> rentalRequests(){
//    	List<RentalRequest> rentals = new ArrayList<>();
//    	String rentalDate1 = "2024, 01, 12";
//    	String returnDate1 = "2024, 04, 22";
//    	String rentalDate2 = "2024, 04, 12";
//    	String returnDate2 = "2024, 05, 23";
//    	
//    	RentalRequest rental1 = RentalRequest.builder()
//    								.id(1L)
//					    			.rentalDate(rentalDate1)
//					    			.returnDate(returnDate1)
//					    			.build();
//    	RentalRequest rental2 = RentalRequest.builder()
//    								.id(2L)
//					    			.rentalDate(rentalDate2)
//					    			.returnDate(returnDate2)
//					    			.build();
//    	rentals.add(rental1);
//    	rentals.add(rental2);
//    	
//    	return rentals;
//    }
//    
//    List<RentalResponse> rentalResponses(){
//    	List<RentalResponse> rentals = new ArrayList<>();
//    	String rentalDate1 = "2024, 01, 12";
//    	String returnDate1 = "2024, 04, 22";
//    	String rentalDate2 = "2024, 04, 12";
//    	String returnDate2 = "2024, 05, 23";
//    	
//    	RentalResponse rental1 = RentalResponse.builder()
//					    			.id(1L)
//					    			.rentalDate(rentalDate1)
//					    			.returnDate(returnDate1)
//					    			.build();
//    	RentalResponse rental2 = RentalResponse.builder()
//					    			.id(2L)
//					    			.rentalDate(rentalDate2)
//					    			.returnDate(returnDate2)
//					    			.build();
//    	rentals.add(rental1);
//    	rentals.add(rental2);
//    	
//    	return rentals;
//    }
//    
//    public List<Customer> buildCustomers() {
//    	List<Customer> customers = new ArrayList<>();
//    	
//    	Customer customer1 = Customer.builder()
//    			.id(customerId)
//    			.firstName("Ali")
//    			.lastName("Bouzar")
//    			.email("ali@email.net")
//    			.rentals(rentals())
//    			.build();
//    	Customer customer2 = Customer.builder()
//    			.id(customerId1)
//    			.firstName("Sam")
//    			.lastName("Ben")
//    			.email("sam@email.net")
//    			.rentals(rentals())
//    			.build();
//    	customers.add(customer1);
//    	customers.add(customer2);
//    	
//    	return customers;
//    }
//    
//    List<Rental> rentals(){
//    	List<Rental> rentals = new ArrayList<>();
//    	String rentalDate1 = "2020, 01, 12";
//    	String returnDate1 = "2020, 04, 22";
//    	String rentalDate2 = "2020, 04, 12";
//    	String returnDate2 = "2020, 05, 23";
//    	
//    	Rental rental1 = Rental.builder()
//					    			.id(1L)
//					    			.rentalDate(rentalDate1)
//					    			.returnDate(returnDate1)
//					    			.build();
//    	Rental rental2 = Rental.builder()
//					    			.id(2L)
//					    			.rentalDate(rentalDate2)
//					    			.returnDate(returnDate2)
//					    			.build();
//    	rentals.add(rental1);
//    	rentals.add(rental2);
//    	
//    	return rentals;
//    };
//    
//    @Test
//	public void getVideoCopyByIdNotFoundExceptionTest() throws Exception {
//		// Arrange
//		Long videoCopyId = 33L;
//		String expectedMessage = "The video Copy with id=" + videoCopyId + " is not available in our database!";
//		String expectedErrorCode = "VIDEOCOPY_NOT_FOUND";
//		HttpStatus expectedHttpStatusStatus = HttpStatus.NOT_FOUND;
//
//		// Act
//		when(videoCopyRepository.findById(videoCopyId)).thenReturn(Optional.empty());
//
//		VideoCopyServiceCustomException exception = assertThrows(VideoCopyServiceCustomException.class,
//				() -> videoCopyService1.getVideoCopyById(videoCopyId));
//		// Assert
//		assertEquals(expectedErrorCode, exception.getErrorCode());
//		assertEquals(expectedMessage, exception.getMessage());
//		assertEquals(expectedHttpStatusStatus, exception.getStatus());
//	}
//
////    private List<RentalResponse> rentalsRequestToRentalsResponseMapper(List<RentalRequest> rentalRequests) {
////		List<RentalResponse> rentalResponses = rentalRequests.stream()
////				.map(rentalReq -> {
////					RentalResponse rentalRes = new RentalResponse();
////					BeanUtils.copyProperties(rentalReq, rentalRes);
////					return rentalRes;
////				}).toList();
////
////		return rentalResponses;
////	}
//    
//    
//    
//    
//    private VideoCopyResponse buildVideoCopyResponse() {
//		return VideoCopyResponse.builder()
//				.id(1L)
//				.available("True")
//				.rentals(rentalResponses())
//				.build();
//	}
//
//	private VideoCopyRequest buildVideoCopyRequest() {
//		return VideoCopyRequest.builder()
//				.id(1L)
//				.available("True")
//				.rentals(rentalRequests())
//				.build();
//	}
//	
//}
