package com.casumo.videorental.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casumo.videorental.dto.VideoRequest;
import com.casumo.videorental.dto.VideoResponse;
import com.casumo.videorental.model.Video;
import com.casumo.videorental.service.VideoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/video-rental-store/api/v1/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos() {
        List<Video> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> getVideoById(@PathVariable(name = "id") Long id) {
    	VideoResponse video = videoService.getVideoById(id);
        if (video != null) {
        	return ResponseEntity.status(HttpStatus.FOUND).body(video);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<VideoResponse> createVideo(@RequestBody VideoRequest videoRequest) {
    	VideoResponse createdVideo = videoService.createVideo(videoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVideo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponse> updateVideo(@PathVariable(name = "id") Long id, @RequestBody VideoRequest videoRequest) {
    	VideoResponse updatedVideo = videoService.updateVideo(id, videoRequest);
        if (updatedVideo != null) {
            return ResponseEntity.ok(updatedVideo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable(name = "id") Long id) {
        boolean deleted = videoService.deleteVideo(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
