package com.casumo.videorental.service;

import java.util.List;

import com.casumo.videorental.dto.VideoCopyRequest;
import com.casumo.videorental.dto.VideoCopyResponse;
import com.casumo.videorental.model.VideoCopy;

public interface VideoCopyService {

	List<VideoCopy> getAllVideoCopies();
	
	VideoCopyResponse getVideoCopyById(Long videoCopyId);
	
	VideoCopyResponse createVideoCopy(VideoCopyRequest videoCopyRequest);
	
	VideoCopyResponse updateVideoCopy(Long videoCopyId, VideoCopyRequest videoCopyRequest);
	
	boolean deleteVideoCopy(Long videoCopyId);
}
