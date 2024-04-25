package com.casumo.videorental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalRequest {
	private Long id;
	private String rentalDate;
	private String returnDate;
//	private VideoCopy videoCopy;
}
