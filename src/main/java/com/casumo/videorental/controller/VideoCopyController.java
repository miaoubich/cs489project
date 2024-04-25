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

import com.casumo.videorental.dto.CustomerRequest;
import com.casumo.videorental.dto.CustomerResponse;
import com.casumo.videorental.dto.VideoCopyRequest;
import com.casumo.videorental.dto.VideoCopyResponse;
import com.casumo.videorental.model.Customer;
import com.casumo.videorental.model.VideoCopy;
import com.casumo.videorental.service.VideoCopyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/video-rental-store/api/v1/videocopy")
@RequiredArgsConstructor
public class VideoCopyController {

    private final VideoCopyService videoCopyService;

    @PostMapping
    public ResponseEntity<VideoCopyResponse> createVideoCopy(@RequestBody VideoCopyRequest videoCopyRequest) {
    	VideoCopyResponse createdVideoCopy = videoCopyService.createVideoCopy(videoCopyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVideoCopy);
    }
    
    @GetMapping("/{videoCopyId}")
    public ResponseEntity<VideoCopyResponse> getVideoCopyById(@PathVariable(name="videoCopyId") Long videoCopyId) {
    	VideoCopyResponse videoCopy = videoCopyService.getVideoCopyById(videoCopyId);
        if (videoCopy != null) {
            return ResponseEntity.ok(videoCopy);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<VideoCopy>> getAllCustomers() {
        List<VideoCopy> VideoCopies = videoCopyService.getAllVideoCopies();
        return ResponseEntity.ok(VideoCopies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoCopyResponse> updateCustomer(@PathVariable(name="id") Long id, @RequestBody VideoCopyRequest videoCopyRequest) {
    	VideoCopyResponse updatedVideoCopy = videoCopyService.updateVideoCopy(id, videoCopyRequest);
        if (updatedVideoCopy!= null) {
            return ResponseEntity.ok(updatedVideoCopy);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(name="id") Long id) {
        boolean deleted = videoCopyService.deleteVideoCopy(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
