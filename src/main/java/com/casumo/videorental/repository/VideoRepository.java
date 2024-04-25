package com.casumo.videorental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casumo.videorental.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{
	
	Video getVideoByVideoTitle(String title);
}
