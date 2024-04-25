package com.casumo.videorental.service;

import java.util.List;

import com.casumo.videorental.dto.VideoRequest;
import com.casumo.videorental.dto.VideoResponse;
import com.casumo.videorental.model.Video;

public interface VideoService {

	List<Video> getAllVideos();
	
	VideoResponse getVideoById(Long videoId);
	
	VideoResponse createVideo(VideoRequest videoRequest);
	
	VideoResponse updateVideo(Long videoId, VideoRequest videoRequest);
	
	boolean deleteVideo(Long videoId);
}
