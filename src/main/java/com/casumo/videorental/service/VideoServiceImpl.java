package com.casumo.videorental.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.casumo.videorental.dto.VideoCopyRequest;
import com.casumo.videorental.dto.VideoCopyResponse;
import com.casumo.videorental.dto.VideoRequest;
import com.casumo.videorental.dto.VideoResponse;
import com.casumo.videorental.exception.VideoServiceCustomException;
import com.casumo.videorental.model.Video;
import com.casumo.videorental.model.VideoCopy;
import com.casumo.videorental.repository.VideoCopyRepository;
import com.casumo.videorental.repository.VideoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoServiceImpl implements VideoService {

	private final VideoRepository videoRepository;
	private final VideoCopyRepository videoCopyRepository;
	
	@Override
	public List<Video> getAllVideos() {
		log.info("Video List is being fetch from the database ...");
		List<Video> videos = videoRepository.findAll();
		log.info("DisplayVideosList: " + videos);
		
		if(videos == null) {
			log.info("No Video found in the database!");
			throw new VideoServiceCustomException("There are no Video yet in our store!", "VIDEO_LIST_EMPTY_ERROR", HttpStatus.NO_CONTENT);
		}
		else {
			log.info("Video List successfully loaded from the database -> " + videos);
			return videos;
		}
	}

	@Override
	public VideoResponse getVideoById(Long videoId) {
		log.info("Searching for a Video with id=" + videoId + " is ongoing ...");
		Video video = videoRepository.findById(videoId).orElseThrow(
				() -> new VideoServiceCustomException(
						"The video with id=" + videoId + " is not available in our database!",
						"VIDEO_NOT_FOUND", HttpStatus.NOT_FOUND));
		
		log.info("The Video Copy with id=" + videoId + " successfully found!");
		
		return mapper(video);
	}

	@Override
	public VideoResponse createVideo(VideoRequest videoRequest) {
		Optional<Video> videoExist = Optional.ofNullable(videoRepository.getVideoByVideoTitle(videoRequest.getVideoTitle()));
		VideoResponse videoResponse = null;
		
		if(!videoExist.isPresent()) {
			log.info("Mapping VideoCopyRequest from VideoRequest Object into VideoCopy object ...");
			List<VideoCopy> videoCopies = videoRequest.getVideoCopies().stream().map(viderCopyRequest -> 
				new VideoCopy(null, viderCopyRequest.getAvailable())
			).toList();
			log.info("VideoCopyRequest Object successfully mapped into VideoCopy object!");
			
			Video video = Video.builder()
					.videoTitle(videoRequest.getVideoTitle())
					.director(videoRequest.getDirector())
					.videoRentalPrice(videoRequest.getVideoRentalPrice())
					.year(videoRequest.getYear())
					.build();
			log.info("Video Object successfully built from VideoRequest");
			
			Video actualVideo = videoRepository.save(video);
			
			List<VideoCopy> savedvideoCopies = new ArrayList<>();
			for(VideoCopy videoCopy : videoCopies) {
				videoCopy.setVideo(actualVideo);
				savedvideoCopies.add(videoCopyRepository.save(videoCopy));
			}
			log.info("Populating savedvideoCopies with video_id's since CascadeType didn't work.");
				
			videoResponse = new VideoResponse();
			BeanUtils.copyProperties(actualVideo, videoResponse);
			List<VideoCopyResponse> videoCopyResponses = savedvideoCopies.stream().map(v -> {
				VideoCopyResponse  videoCopyResponse = new VideoCopyResponse();
				videoCopyResponse.setId(v.getId());
				videoCopyResponse.setAvailable(v.getAvailable());
				
				return videoCopyResponse;
				}).toList();
			videoResponse.setVideoCopies(videoCopyResponses);
			log.info("A Video was successfully created!");
		}
		else {
			throw new VideoServiceCustomException(
					"The video with Video Title: - " + videoRequest.getVideoTitle() + " - already exist!",
					"VIDEO_EXIST", HttpStatus.FOUND);
		}
		log.info("Video " + videoRequest.getVideoTitle() + " already exist!");
		return videoResponse;
	}

	@Override
	public VideoResponse updateVideo(Long videoId, VideoRequest videoRequest) {
		Video existVideo = videoRepository.findById(videoId).orElseThrow(
				() -> new VideoServiceCustomException(
						"The video with id=" + videoId + " is not available in our database!",
						"VIDEO_NOT_FOUND", HttpStatus.NOT_FOUND));
		
		existVideo.setDirector(videoRequest.getDirector());
		existVideo.setVideoRentalPrice(videoRequest.getVideoRentalPrice());
		existVideo.setVideoTitle(videoRequest.getVideoTitle());
		existVideo.setYear(videoRequest.getYear());
		existVideo = videoRepository.save(existVideo);
		
		if(existVideo.getVideoCopies() != null && videoRequest.getVideoCopies() != null) {
			existVideo.getVideoCopies().stream()
				.map(existVideoCopy -> {
					VideoCopyRequest videoCopyRequest = videoRequest.getVideoCopies().stream().filter(v -> v.getId() == existVideoCopy.getId())
							.findFirst().get();
					existVideoCopy.setAvailable(videoCopyRequest.getAvailable());
					videoCopyRepository.save(existVideoCopy);
					
					return existVideoCopy;
				}).toList();
		}
		return mapper(existVideo);
	}

	@Override
	public boolean deleteVideo(Long videoId) {
		if(getVideoById(videoId) != null) {
			videoRepository.deleteById(videoId);
			log.info("Video seccessfully deleted!");
			return true;
		}
		return false;
	}
	
	private VideoResponse mapper(Video video) {
		VideoResponse videoResponse = new VideoResponse();
		BeanUtils.copyProperties(video, videoResponse);
		
		List<VideoCopyResponse> videoCopyResponses = video.getVideoCopies().stream()
				.map(videoCopy -> {
					VideoCopyResponse videoCopyRes = new VideoCopyResponse();
					BeanUtils.copyProperties(videoCopy, videoCopyRes);
					return videoCopyRes;
				}).toList();
		videoResponse.setVideoCopies(videoCopyResponses);

		return videoResponse;
	}

}
