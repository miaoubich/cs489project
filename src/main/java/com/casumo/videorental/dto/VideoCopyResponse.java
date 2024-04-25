package com.casumo.videorental.dto;

import java.util.List;

import com.casumo.videorental.model.Customer;
import com.casumo.videorental.model.Video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoCopyResponse {

	private Long id;
	private List<RentalResponse> rentals;
    private String available;
}
