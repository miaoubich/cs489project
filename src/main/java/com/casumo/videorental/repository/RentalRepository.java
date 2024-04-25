package com.casumo.videorental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casumo.videorental.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

}
