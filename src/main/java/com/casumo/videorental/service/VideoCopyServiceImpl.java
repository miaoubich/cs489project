package com.casumo.videorental.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.casumo.videorental.dto.RentalRequest;
import com.casumo.videorental.dto.RentalResponse;
import com.casumo.videorental.dto.VideoCopyRequest;
import com.casumo.videorental.dto.VideoCopyResponse;
import com.casumo.videorental.exception.VideoCopyServiceCustomException;
import com.casumo.videorental.model.Rental;
import com.casumo.videorental.model.VideoCopy;
import com.casumo.videorental.repository.RentalRepository;
import com.casumo.videorental.repository.VideoCopyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoCopyServiceImpl implements VideoCopyService {

	private final VideoCopyRepository videoCopyRepository;
	private final RentalRepository rentalRepository;
	
	
	@Override
	public List<VideoCopy> getAllVideoCopies() {
		List<VideoCopy> videoCopies = videoCopyRepository.findAll();
		log.info("Video Copy List is being fetch from the database ...");
		
		if(videoCopies == null) {
			log.info("No Video Copy found in the database!");
			throw new VideoCopyServiceCustomException("There are no Video Copy yet in our store!", "VIDEOCOPY_LIST_EMPTY_ERROR", HttpStatus.NO_CONTENT);
		}
		else {
			log.info("Video Copy List successfully loaded from the database -> " + videoCopies);
			return videoCopies;
		}
	}

	@Override
	public VideoCopyResponse getVideoCopyById(Long videoCopyId) {
		log.info("Searching for a Video Copy with id=" + videoCopyId + " is ongoing ...");
		VideoCopy videoCopy = videoCopyRepository.findById(videoCopyId).orElseThrow(
				() -> new VideoCopyServiceCustomException(
						"The video Copy with id=" + videoCopyId + " is not available in our database!",
						"VIDEOCOPY_NOT_FOUND", HttpStatus.NOT_FOUND));
		
		log.info("The Video Copy with id=" + videoCopyId + " successfully found!");
		
		return mapper(videoCopy);
	}

	@Override
	public VideoCopyResponse createVideoCopy(VideoCopyRequest videoCopyRequest) {
		log.info("Mapping RentalRequest from VideoCopyRequest Object into Rental object ...");
		List<Rental> rentals = videoCopyRequest.getRentals().stream().map(rentalRequest -> 
			new Rental(rentalRequest.getRentalDate(), rentalRequest.getReturnDate())
		).toList();
		log.info("RentalRequest Object successfully mapped into Rental object!");
		
		VideoCopy videoCopy = VideoCopy.builder()
				.available(videoCopyRequest.getAvailable())
				.build();
		videoCopy.setRentals(rentals);
		log.info("VideoCopy Object successfully built from VideoCopyRequest");
		
		VideoCopy actualVideoCopy = videoCopyRepository.save(videoCopy);
		
		var savedRentals = new ArrayList<Rental>();
		for(var rental : rentals) {
			rental.setVideoCopy(actualVideoCopy);
			savedRentals.add(rentalRepository.save(rental));
		}
		log.info("Populating rentals with videoCopy_id's since CascadeType didn't work.");
			
		VideoCopyResponse videoCopyResponse = new VideoCopyResponse();
		BeanUtils.copyProperties(actualVideoCopy, videoCopyResponse);
		List<RentalResponse> rentalResponses = savedRentals.stream().map(r -> new RentalResponse(r.getId(), r.getRentalDate(), r.getReturnDate())).toList();
		videoCopyResponse.setRentals(rentalResponses);
		log.info("A customer was successfully created!");
		
		return videoCopyResponse;
	}

	@Override
	public VideoCopyResponse updateVideoCopy(Long videoCopyId, VideoCopyRequest videoCopyRequest) {
		VideoCopy existVideoCopy = videoCopyRepository.findById(videoCopyId).orElseThrow(
				() -> new VideoCopyServiceCustomException(
						"The video Copy with id=" + videoCopyId + " is not available in our database!",
						"VIDEOCOPY_NOT_FOUND", HttpStatus.NOT_FOUND));
		
		existVideoCopy.setAvailable(videoCopyRequest.getAvailable());
		existVideoCopy = videoCopyRepository.save(existVideoCopy);
		
		if(existVideoCopy.getRentals() != null && videoCopyRequest.getRentals() != null) {
			existVideoCopy.getRentals().stream()
				.map(existRental -> {
					RentalRequest rentalRequest = videoCopyRequest.getRentals().stream().filter(r -> existRental.getId() == r.getId())
							.findFirst().get();
					existRental.setRentalDate(rentalRequest.getRentalDate());
					existRental.setReturnDate(rentalRequest.getReturnDate());
					rentalRepository.save(existRental);
					
					return existRental;
				}).toList();
		}
		return mapper(existVideoCopy);
	}

	@Override
	public boolean deleteVideoCopy(Long videoCopyId) {
		if(getVideoCopyById(videoCopyId) != null) {
			videoCopyRepository.deleteById(videoCopyId);
			log.info("Video Copy seccessfully deleted!");
			return true;
		}
		return false;
	}
	
	private VideoCopyResponse mapper(VideoCopy videoCopy) {
		VideoCopyResponse videoCopyResponse = new VideoCopyResponse();
		BeanUtils.copyProperties(videoCopy, videoCopyResponse);
		
		List<RentalResponse> rentals = videoCopy.getRentals().stream()
				.map(rental -> {
					RentalResponse rentalRes = new RentalResponse();
					BeanUtils.copyProperties(rental, rentalRes);
					return rentalRes;
				}).toList();
		videoCopyResponse.setRentals(rentals);

		return videoCopyResponse;
	}

}
