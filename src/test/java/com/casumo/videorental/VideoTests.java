//package com.casumo.videorental;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.casumo.videorental.dto.VideoCopyRequest;
//import com.casumo.videorental.dto.VideoCopyResponse;
//import com.casumo.videorental.dto.VideoRequest;
//import com.casumo.videorental.dto.VideoResponse;
//import com.casumo.videorental.exception.VideoServiceCustomException;
//import com.casumo.videorental.model.Rental;
//import com.casumo.videorental.model.Video;
//import com.casumo.videorental.model.VideoCopy;
//import com.casumo.videorental.repository.VideoRepository;
//import com.casumo.videorental.service.VideoService;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@SpringBootTest
//@RequiredArgsConstructor
//@Slf4j
//public class VideoTests {
//
//	@Autowired
//	VideoService videoService1;
//	@Mock
//	VideoService videoService;
//	@Mock
//	VideoRepository videoRepository;
//
//	private final Long VIDEO_ID1 = 1L;
//	private final Long VIDEO_ID2 = 2L;
//	private final String VIDEO_TITLE = "DIE HARD";
//	private final String DIRECTOR = "BRUCE Willis";
//	private final double VIDEO_RENTAL_PRICE = 12.33;
//	private final Integer YEAR = 1998;
//	private final String VIDEO_TITLE2 = "The Nutty Professor II";
//	private final String DIRECTOR2 = "Eddie Murfey";
//	private final double VIDEO_RENTAL_PRICE2 = 11.13;
//	private final Integer YEAR2 = 2002;
//	private Video VIDEO = buildVideo();
//	private List<Video> VIDEOS = new ArrayList<>();
//	private List<VideoCopy> VIDEO_COPIES = new ArrayList<>();
//
//	private final Long VIDEOCOPY_ID1 = 1L;
//	private final Long VIDEOCOPY_ID2 = 2L;
//	private final String AVAILABLE_TRUE = "TRUE";
//	private final String AVAILABLE_FALSE = "FALSE";
//
//	@BeforeEach
//	public void setUp() {
//		MockitoAnnotations.openMocks(this);
//	}
//
//	@Order(1)
//	@Test
//	public void createVideoTest() throws Exception {
//		// Arrange
//		Video video = buildVideo();
//		VideoResponse expectedResult = buildVideoResponse();
//		List<VideoCopyRequest> videoCopyRequests = new ArrayList<>();
//		VideoCopyRequest videoCopyRequest1 = VideoCopyRequest.builder()
//				.id(VIDEOCOPY_ID1)
//				.rentals(null)
//				.available(AVAILABLE_FALSE)
//				.build();
//		VideoCopyRequest videoCopyRequest2 = VideoCopyRequest.builder()
//				.id(VIDEOCOPY_ID2)
//				.rentals(null)
//				.available(AVAILABLE_TRUE)
//				.build();
//		videoCopyRequests.add(videoCopyRequest1);
//		videoCopyRequests.add(videoCopyRequest2);
//
//		VideoRequest videoRequest = VideoRequest.builder().id(VIDEO_ID1).videoTitle(VIDEO_TITLE).director(DIRECTOR)
//				.videoRentalPrice(VIDEO_RENTAL_PRICE).year(YEAR).videoCopies(videoCopyRequests).build();
//
//		// Act
//		when(videoRepository.save(Video.builder().id(VIDEO_ID1).videoTitle(VIDEO_TITLE).director(DIRECTOR)
//				.videoRentalPrice(VIDEO_RENTAL_PRICE).year(YEAR).videoCopies(VIDEO_COPIES).build())).thenReturn(video);
//
//		VideoResponse actualResult = videoService1.createVideo(videoRequest);
//
//		// Assert
//		assertEquals(actualResult.getId(), expectedResult.getId());
//		assertEquals(actualResult.getVideoCopies(), expectedResult.getVideoCopies());
//		assertEquals(actualResult.getDirector(), expectedResult.getDirector());
//		assertEquals(actualResult.getVideoRentalPrice(), expectedResult.getVideoRentalPrice());
//		assertEquals(actualResult.getVideoTitle(), expectedResult.getVideoTitle());
//		assertEquals(actualResult.getYear(), expectedResult.getYear());
//
//	}
//
//	@Order(2)
//	@Test
//	public void getVideoByIdTest() throws Exception {
//		// Arrange
//		Video expectedResult = buildVideo();
//
//		// Act
//		// Mocking the behavior of videoRepository.findById(id)
//		when(videoRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedResult));
//
//		Video actualResult = videoRepository.findById(VIDEO_ID1).get();
//		log.info("foundvideo: " + actualResult);
//
//		// Verify that the repository method was called with the correct argument
//		verify(videoRepository).findById(VIDEO_ID1);
//
//		// Assert
//		assertNotNull(actualResult);
//		assertEquals(expectedResult, actualResult);
//		assertEquals(expectedResult.getVideoTitle(), actualResult.getVideoTitle());
//	}
//
//	@Order(3)
//	@Test
//	public void getVideoByIdNotFoundExceptionTest() throws Exception {
//		// Arrange
//		Long videoId = 33L;
//		String expectedMessage = "The video with id=" + videoId + " is not available in our database!";
//		String expectedErrorCode = "VIDEO_NOT_FOUND";
//		HttpStatus expectedHttpStatusStatus = HttpStatus.NOT_FOUND;
//
//		// Act
//		when(videoRepository.findById(videoId)).thenReturn(Optional.empty());
//
//		VideoServiceCustomException exception = assertThrows(VideoServiceCustomException.class,
//				() -> videoService1.getVideoById(videoId));
//		// Assert
//		assertEquals(expectedErrorCode, exception.getErrorCode());
//		assertEquals(expectedMessage, exception.getMessage());
//		assertEquals(expectedHttpStatusStatus, exception.getStatus());
//	}
//	
//	@Order(4)
//	@Test
//	public void getAllVideosTest() throws Exception {
//		// Mocked list of videos
//		List<Video> mockedVideos = new ArrayList<>();
//		mockedVideos = buildVideos();
//
//		// Mock videoService.getAllVideos() to return the mocked list of videos
//		when(videoRepository.findAll()).thenReturn(mockedVideos);
//
//		List<Video> foundVideosList = videoRepository.findAll();
//
//		log.info("foundVideosList: " + foundVideosList);
//
//		assertEquals(mockedVideos.size(), foundVideosList.size());
//
//	}
//
//	@Order(5)
//	@Test
//	public void updateVideoTest() throws Exception {
//		// Mock data
//		VideoRequest videoRequest = buildVideoRequest();
//		VideoResponse updatedVideoResponse = buildVideoResponse();
//
//		when(videoService.updateVideo(any(), any(VideoRequest.class))).thenReturn(updatedVideoResponse);
//
//		VideoResponse actualResult = videoService.updateVideo(VIDEOCOPY_ID1, videoRequest);
//
//		log.info("actualResult: " + actualResult);
//		assertEquals(updatedVideoResponse, actualResult);
//	}
//
//	@Order(6)
//	@Test
//    public void deleteVideoTest() throws Exception {
//    	// Verify that the service method was called with the correct videoID
//    	when(videoService.deleteVideo(VIDEO_ID1)).thenReturn(true);
//    	Boolean actualResult = videoService.deleteVideo(VIDEOCOPY_ID1);
////    	Boolean actualResult = videoService.deleteVideo(VIDEOCOPY_ID2);
//    	
//    	assertTrue(actualResult);
//    }
//
//	private List<Video> buildVideos() {
//		VIDEOS = new ArrayList<>();
//		VIDEO_COPIES = buildVideoCopies();
//
//		Video video1 = Video.builder().id(VIDEO_ID1).videoTitle(VIDEO_TITLE).director(DIRECTOR)
//				.videoRentalPrice(VIDEO_RENTAL_PRICE).year(YEAR).videoCopies(VIDEO_COPIES).build();
//		Video video2 = Video.builder().id(VIDEO_ID2).videoTitle(VIDEO_TITLE2).director(DIRECTOR2)
//				.videoRentalPrice(VIDEO_RENTAL_PRICE2).year(YEAR2).videoCopies(VIDEO_COPIES).build();
//		VIDEOS.add(video1);
////		VIDEOS.add(video2);
//
//		return VIDEOS;
//	}
//
//	private List<VideoCopy> buildVideoCopies() {
//		List<Rental> rentals = rentals();
//		VIDEO = buildVideo();
//
//		VideoCopy videoCopy1 = VideoCopy.builder().id(VIDEOCOPY_ID1).rentals(rentals).video(VIDEO)
//				.available(AVAILABLE_TRUE).build();
//		VideoCopy videoCopy2 = VideoCopy.builder().id(VIDEOCOPY_ID2).rentals(null).video(VIDEO)
//				.available(AVAILABLE_TRUE).build();
//		VIDEO_COPIES.add(videoCopy1);
//		VIDEO_COPIES.add(videoCopy2);
//
//		return VIDEO_COPIES;
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
//	private VideoRequest buildVideoRequest() {
//		return VideoRequest.builder().id(VIDEO_ID1).director(DIRECTOR).videoTitle(VIDEO_TITLE)
//				.videoRentalPrice(VIDEO_RENTAL_PRICE).year(YEAR).videoCopies(Collections.emptyList()).build();
//	}
//
//	private VideoResponse buildVideoResponse() {
//		List<VideoCopyResponse> videoCopyResponses = new ArrayList<>();
//		VideoCopyResponse videoCopyResponse1 = VideoCopyResponse.builder()
//				.id(VIDEOCOPY_ID1)
//				.rentals(null)
//				.available(AVAILABLE_FALSE)
//				.build();
//		VideoCopyResponse videoCopyResponse2 = VideoCopyResponse.builder()
//				.id(VIDEOCOPY_ID2)
//				.rentals(null)
//				.available(AVAILABLE_TRUE)
//				.build();
//		videoCopyResponses.add(videoCopyResponse1);
//		videoCopyResponses.add(videoCopyResponse2);
//
//		return VideoResponse.builder()
//				.id(VIDEO_ID1)
//				.director(DIRECTOR)
//				.videoTitle(VIDEO_TITLE)
//				.videoRentalPrice(VIDEO_RENTAL_PRICE)
//				.year(YEAR)
//				.videoCopies(videoCopyResponses)
//				.build();
//	}
//
//	private Video buildVideo() {
//		Video video = Video.builder().id(VIDEO_ID1).videoTitle(VIDEO_TITLE).director(DIRECTOR)
//				.videoTitle(VIDEO_TITLE).videoRentalPrice(VIDEO_RENTAL_PRICE).year(YEAR).videoCopies(VIDEO_COPIES).build();
//		return video;
//	}
//
////	private List<VideoCopyResponse> buildVideoCopyResponses() {
////		VIDEO_COPY_RESPONSES = new ArrayList<>();
////
////		VideoCopyResponse videoResponse1 = VideoCopyResponse.builder().id(VIDEOCOPY_ID1).available(AVAILABLE_TRUE)
////				.build();
////		VideoCopyResponse videoResponse2 = VideoCopyResponse.builder().id(VIDEOCOPY_ID2).available(AVAILABLE_FALSE)
////				.build();
////		VIDEO_COPY_RESPONSES.add(videoResponse1);
////		VIDEO_COPY_RESPONSES.add(videoResponse2);
////
////		return VIDEO_COPY_RESPONSES;
////	}
//}
