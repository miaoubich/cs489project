//package com.casumo.videorental;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.casumo.videorental.dto.VideoCopyResponse;
//import com.casumo.videorental.dto.VideoRequest;
//import com.casumo.videorental.dto.VideoResponse;
//import com.casumo.videorental.model.Rental;
//import com.casumo.videorental.model.Video;
//import com.casumo.videorental.model.VideoCopy;
//import com.casumo.videorental.repository.VideoRepository;
//import com.casumo.videorental.service.VideoService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@RequiredArgsConstructor
//@Slf4j
//class VideoControllerTests {
//
//	@Autowired
//	MockMvc mockMvc;
//	@Autowired
//    private ObjectMapper mapper;
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
//	private final Long VIDEOCOPY_ID2 = 1L;
//	private final String AVAILABLE_TRUE = "TRUE";
//	private final String AVAILABLE_FALSE = "FALSE";
//	private List<VideoCopyResponse> VIDEO_COPY_RESPONSES = null;
//	
//    @Test
//    public void createVideoTest() throws Exception {
//        // Create a VideoRequest object to send in the request body
//        VideoRequest videoRequest = VideoRequest.builder()
//    			.id(1L)
//                .director("Sample Director")
//                .videoTitle("Sample Video")
//                .videoRentalPrice(10.5)
//                .year(2015)
//                .videoCopies(Collections.emptyList())
//    			.build();
//
//        // Create a VideoResponse object to mock the service response
//        VideoResponse mockedResponse = VideoResponse.builder()
//        		.id(1L)
//                .director("Sample Director")
//                .videoTitle("Sample Video")
//                .videoRentalPrice(10.5)
//                .year(2015)
//                .videoCopies(Collections.emptyList())
//    			.build();
//
//        // Mock videoService.createVideo method to return the mocked response
//        when(videoService.createVideo(any(VideoRequest.class))).thenReturn(mockedResponse);
//
////        MvcResult result = 
//        		mockMvc.perform(post("/video-rental-store/api/v1/videos")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(videoRequest)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(jsonPath("$.id").exists())
//                .andReturn();
//    }
//
//    
//    @Test
//    public void  getVideoByIdTest() throws Exception {
//    	VideoResponse videoResponse = buildVideoResponse();
//    	VIDEO_COPY_RESPONSES = buildVideoCopyResponses();
//    	
//    	when(videoService.getVideoById(VIDEO_ID1)).thenReturn(videoResponse);
//    	
//    	this.mockMvc.perform(get("/video-rental-store/api/v1/videos/{videoId}", VIDEO_ID1))
//    				.andDo(print())
//    				.andExpect(status().isFound())
//    				.andExpect(status().isCreated())
//        			.andExpect(jsonPath("id").value(VIDEO_ID1))
//        			.andExpect(jsonPath("$.director").value(DIRECTOR))
//        			.andExpect(jsonPath("$.videoRentalPrice").value(VIDEO_RENTAL_PRICE))
//        			.andExpect(jsonPath("$.YEAR").value(YEAR))
//        			.andExpect(jsonPath("$videoCopies").value(VIDEO_COPY_RESPONSES));
//    				
//    }
//    
//    @Test
//    public void getAllVideosTest() throws Exception {
//        // Mocked list of videos
//        List<Video> mockedVideos = buildVideos();
//
//        // Mock videoService.getAllVideos() to return the mocked list of videos
//        when(videoService.getAllVideos()).thenReturn(mockedVideos);
//
//        // Perform GET request to /video-rental-store/api/v1/videos
//        mockMvc.perform(get("/video-rental-store/api/v1/videos")
//                .contentType(MediaType.APPLICATION_JSON))
//        		.andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].videoTitle").value("Sample Video"))
//                .andExpect(jsonPath("$[0].director").value("Sample Director"))
//                .andExpect(jsonPath("$[0].videoRentalPrice").value(10.5))
//                .andExpect(jsonPath("$[0].year").value(2015))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].videoTitle").value("Sample Video"))
//                .andExpect(jsonPath("$[1].director").value("Sample Director"))
//                .andExpect(jsonPath("$[1].videoRentalPrice").value(10.5))
//                .andExpect(jsonPath("$[1].year").value(2015));
//    }
//    
//    @Test
//    public void updateVideoTest() throws Exception {
//        // Mock data
//        VideoRequest videoRequest = buildVideoRequest();
//        VideoResponse updatedVideoResponse = buildVideoResponse();
//        
//        when(videoService.updateVideo(any(), any(VideoRequest.class))).thenReturn(updatedVideoResponse);
//        when(videoService.updateVideo(any(), any())).thenReturn(updatedVideoResponse);
//    	when(videoService.updateVideo(VIDEO_ID1, videoRequest)).thenReturn(updatedVideoResponse);
//
//        // Perform PUT request
//        mockMvc.perform(put("/video-rental-store/api/v1/videos/{id}", VIDEO_ID1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(videoRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(VIDEO_ID1))
//                .andExpect(jsonPath("$.videoTitle").value(videoRequest.getVideoTitle()))
//                .andExpect(jsonPath("$.director").value(videoRequest.getDirector()))
//                .andExpect(jsonPath("$.year").value(videoRequest.getYear()))
//                .andExpect(jsonPath("$.videoRentalPrice").value(videoRequest.getVideoRentalPrice()));
//    }
//
//    @Test
//    public void deleteVideoTest() throws Exception {
//    	// Verify that the service method was called with the correct video ID
//    	when(videoService.deleteVideo(VIDEO_ID1)).thenReturn(true);
//    	
//    	 // Perform DELETE request to delete video
//    	mockMvc.perform(delete("/video-rental-store/api/v1/videos/{id}", VIDEO_ID1)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }
//    
//    private VideoRequest buildVideoRequest(){
//    	return VideoRequest.builder()
//    			.id(VIDEO_ID1)
//                .director(DIRECTOR)
//                .videoTitle(VIDEO_TITLE)
//                .videoRentalPrice(VIDEO_RENTAL_PRICE)
//                .year(YEAR)
//                .videoCopies(Collections.emptyList())
//    			.build();
//    }
//    
//    
//    private VideoResponse buildVideoResponse() {
//    	VIDEO_COPY_RESPONSES = buildVideoCopyResponses();
//    	
//		return VideoResponse.builder()
//				.id(VIDEO_ID1)
//				.director(DIRECTOR)
//				.videoRentalPrice(VIDEO_RENTAL_PRICE)
//				.year(YEAR)
//				.videoCopies(VIDEO_COPY_RESPONSES)
//				.build();
//	}
//    
//    private List<VideoCopyResponse> buildVideoCopyResponses(){
//    	VIDEO_COPY_RESPONSES = new ArrayList<>();
//    	
//    	VideoCopyResponse videoResponse1 = VideoCopyResponse.builder()
//    			.id(VIDEOCOPY_ID1)
//    			.available(AVAILABLE_TRUE )
//    			.build();
//    	VideoCopyResponse videoResponse2 = VideoCopyResponse.builder()
//    			.id(VIDEOCOPY_ID2)
//    			.available(AVAILABLE_FALSE )
//    			.build();
//    	VIDEO_COPY_RESPONSES.add(videoResponse1);
//    	VIDEO_COPY_RESPONSES.add(videoResponse2);
//    	
//    	return VIDEO_COPY_RESPONSES;
//    }
//    
//    private List<Video> buildVideos(){
//    	VIDEOS = new ArrayList<>();
//    	VIDEO_COPIES = buildVideoCopies();
//    	
//    	Video video1 = Video.builder()
//    			.id(VIDEO_ID1)
//    			.videoTitle(VIDEO_TITLE)
//    			.director(DIRECTOR)
//    			.videoRentalPrice(VIDEO_RENTAL_PRICE)
//    			.year(YEAR)
//    			.videoCopies(VIDEO_COPIES)
//    			.build();
//    	Video video2 = Video.builder()
//    			.id(VIDEO_ID2)
//    			.videoTitle(VIDEO_TITLE2)
//    			.director(DIRECTOR2)
//    			.videoRentalPrice(VIDEO_RENTAL_PRICE2)
//    			.year(YEAR2)
//    			.videoCopies(VIDEO_COPIES)
//    			.build();
//    	VIDEOS.add(video1);
//    	VIDEOS.add(video2);
//    	
//    	return VIDEOS;
//    }
//    
//    private List<VideoCopy> buildVideoCopies(){
//    	List<Rental> rentals = rentals();
//    	VIDEO = buildVideo();
//    	
//    	VideoCopy videoCopy1 = VideoCopy.builder()
//    			.id(VIDEOCOPY_ID1)
//    			.rentals(rentals)
//    			.video(VIDEO)
//    			.available(AVAILABLE_TRUE)
//    			.build();
//    	VideoCopy videoCopy2 = VideoCopy.builder()
//    			.id(VIDEOCOPY_ID2)
//    			.rentals(null)
//    			.video(VIDEO)
//    			.available(AVAILABLE_TRUE)
//    			.build();
//    	VIDEO_COPIES.add(videoCopy1);
//    	VIDEO_COPIES.add(videoCopy2);
//    	
//    	return VIDEO_COPIES;
//    }
//    
//    private Video buildVideo() {
//    	Video video = Video.builder()
//    			.id(VIDEO_ID1)
//    			.videoTitle(VIDEO_TITLE)
//    			.director(DIRECTOR)
//    			.videoRentalPrice(VIDEO_RENTAL_PRICE)
//    			.year(YEAR)
//    			.videoCopies(VIDEO_COPIES)
//    			.build();
//    	return video;
//    }
//
//    List<Rental> rentals(){
//    	List<Rental> rentals = new ArrayList<>();
//    	String rentalDate1 = "2020, 11, 10";
//    	String returnDate1 = "2024, 04, 02";
//    	String rentalDate2 = "2023, 05, 12";
//    	String returnDate2 = "2025, 06, 23";
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
//    }
//
//}
