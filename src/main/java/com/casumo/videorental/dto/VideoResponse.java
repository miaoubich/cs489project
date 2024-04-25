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
public class VideoResponse {

	private Long id;
	private String videoTitle;
	private String director;
	private double videoRentalPrice;
	private Integer year;
	private List<VideoCopyResponse> videoCopies;
}
