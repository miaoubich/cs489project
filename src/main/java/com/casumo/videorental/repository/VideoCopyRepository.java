package com.casumo.videorental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casumo.videorental.model.VideoCopy;

public interface VideoCopyRepository extends JpaRepository<VideoCopy, Long> {

}
