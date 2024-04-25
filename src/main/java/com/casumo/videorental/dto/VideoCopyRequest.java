package com.casumo.videorental.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoCopyRequest {

	private Long id;
	private List<RentalRequest> rentals;
	private String available;
}
