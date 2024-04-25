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
public class CustomerResponse {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private List<RentalResponse> rentals;
}
