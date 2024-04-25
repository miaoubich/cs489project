//package com.casumo.videorental;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.casumo.videorental.dto.RentalRequest;
//import com.casumo.videorental.dto.RentalResponse;
//import com.casumo.videorental.dto.VideoCopyRequest;
//import com.casumo.videorental.dto.VideoCopyResponse;
//import com.casumo.videorental.exception.VideoCopyServiceCustomException;
//import com.casumo.videorental.model.Rental;
//import com.casumo.videorental.model.VideoCopy;
//import com.casumo.videorental.repository.VideoCopyRepository;
//import com.casumo.videorental.service.VideoCopyService;
//
//import lombok.extern.slf4j.Slf4j;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Slf4j
//public class VideoCopyTest {
//
//	@Autowired
//	private VideoCopyService videoCopyService;
//	@Mock
//	private VideoCopyRepository videoCopyRepository;
//	
//	@Test
//	@DisplayName("Register new Video in the database!")
//	public void createVideoCopyTest() {
//		// Arrange
//		VideoCopyResponse expectedResult = buildVideoCopyResponse();
//		VideoCopyRequest videoCopyRequest = buildVideoCopyRequest();
//		
//		// Act
//		when(videoCopyRepository.save(any())).thenReturn(expectedResult);
//		
//		VideoCopyResponse actualResult = videoCopyService.createVideoCopy(videoCopyRequest);
//	
//		log.info("actualResult: " + actualResult);
//		// Assert
//		assertEquals(expectedResult, actualResult);
//	}
//	
//	@Test
//	@DisplayName("Find VideoCopy With a given viceoCopyId!")
//	public void getVideoCopyByIdTest() {
//		// Arrange
//		long videoCopyId = 1L;		
//		VideoCopyResponse expectedResult = buildVideoCopyResponse();
//		List<Rental> rentals = new ArrayList<>();
//		Rental rental1 = Rental.builder()
//				.id(1L)
//				.rentalDate("2024-02-03")
//				.returnDate("2024-02-05")
//				.build();
//		Rental rental2 = Rental.builder()
//				.id(2L)
//				.rentalDate("2024-02-04")
//				.returnDate("2024-02-05")
//				.build();
//		rentals.add(rental1);
//		rentals.add(rental2);
//		
//		Optional<VideoCopy> videoCopy = Optional.ofNullable(VideoCopy.builder()
//				.id(1L)
//				.available("true")
//				.rentals(rentals)
//				.build());
//		
//		// Act
//		when(videoCopyRepository.findById(videoCopyId)).thenReturn(videoCopy);
//		VideoCopyResponse actualResult = videoCopyService.getVideoCopyById(videoCopyId);
//		
//		log.info("actualResult: " + actualResult);
//		//assert
//		assertEquals(expectedResult, actualResult);
//		
//	}
//
//	@Test
//	@DisplayName("Test Error Message for a non existing VideoCopy With a viceoCopyId!")
//	public void videoCopyByIdNotFoundTest() {
//		// Arrange
//		long videoCopyId = 0L;		
//		String expectedMessage = "The video Copy with id=" + videoCopyId + " is not available in our database!";
//		String expectedErrorCode = "VIDEOCOPY_NOT_FOUND";
//		HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
//		
//		// Act
//		when(videoCopyRepository.findById(videoCopyId)).thenReturn(null);
//
//		 VideoCopyServiceCustomException exception = assertThrows(VideoCopyServiceCustomException.class,
//	                () -> videoCopyService.getVideoCopyById(videoCopyId));
//		
//		log.info("actualResult: " + exception);
//		//assert
//		assertEquals(expectedMessage, exception.getMessage());
//		assertEquals(expectedErrorCode, exception.getErrorCode());
//		assertEquals(expectedStatus, exception.getStatus());
//		
//	}
//	
//	@Test
//	@DisplayName("Test update an existing VideCopy in the Database!")
//	public void updateVideoCopyTest() {
//		// Arrange - Mock data
//		long videoCopyId = 1L;
//		VideoCopyRequest videoCopyRequest = buildVideoCopyRequest();
//		VideoCopyResponse expectedResult = buildVideoCopyResponse();
//
//		// Act
//		VideoCopyResponse actualResult = videoCopyService.updateVideoCopy(videoCopyId, videoCopyRequest);
//
//		log.info("actualResult: " + actualResult);
//		// Assert
//		assertEquals(expectedResult, actualResult);
//		assertEquals(expectedResult.getAvailable(), actualResult.getAvailable());
//		assertEquals(expectedResult.getId(), actualResult.getId());
//		assertEquals(expectedResult.getRentals(), actualResult.getRentals());
//	}
//	
//	@Test
//	@DisplayName("Test Delete a VideCopy by videoCopyId!")
//    public void deleteVideoTest() throws Exception {
//		// Arrange
//		Long videoId1 = 1L;
//		
//		// Act
//    	Boolean actualResult = videoCopyService.deleteVideoCopy(videoId1);
////    	Boolean actualResult = videoService.deleteVideo(VIDEOCOPY_ID2);
//    	
//    	// Assert
//    	assertTrue(actualResult);
//    }
//	
//	private VideoCopyRequest buildVideoCopyRequest() {
//		List<RentalRequest> rentalRequests = new ArrayList<>();
//		RentalRequest rentalRequest1 = RentalRequest.builder()
//				.id(1L)
//				.rentalDate("2024-02-03")
//				.returnDate("2024-03-06")
//				.build();
//		RentalRequest rentalRequest2 = RentalRequest.builder()
//				.id(2L)
//				.rentalDate("2024-02-04")
//				.returnDate("2024-02-05")
//				.build();
//		rentalRequests.add(rentalRequest1);
//		rentalRequests.add(rentalRequest2);
//		VideoCopyRequest videoCopyRequest = VideoCopyRequest.builder()
//				.id(1L)
//				.available("true")
//				.rentals(rentalRequests)
//				.build();
//		
//		return videoCopyRequest;
//	}
//	
//	private VideoCopyResponse buildVideoCopyResponse() {
//		List<RentalResponse> rentalResponses = new ArrayList<>();
//		RentalResponse rentalResponse1 = RentalResponse.builder()
//				.id(1L)
//				.rentalDate("2024-02-03")
//				.returnDate("2024-03-06")
//				.build();
//		RentalResponse rentalResponse2 = RentalResponse.builder()
//				.id(2L)
//				.rentalDate("2024-02-04")
//				.returnDate("2024-02-05")
//				.build();
//		rentalResponses.add(rentalResponse1);
//		rentalResponses.add(rentalResponse2);
//		VideoCopyResponse videoCopyRequest = VideoCopyResponse.builder()
//				.id(1L)
//				.available("true")
//				.rentals(rentalResponses)
//				.build();
//		
//		return videoCopyRequest;
//	}
//}
