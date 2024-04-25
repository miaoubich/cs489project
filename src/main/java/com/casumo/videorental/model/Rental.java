package com.casumo.videorental.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Rentals")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Rental {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Rental Date must not be empty nor containing blank spaces!")
	private String rentalDate;
	@NotBlank(message = "Return Date must not be empty nor containing blank spaces!")
	private String returnDate;

	@ManyToOne
	@JoinColumn(name = "customer_id")
//	@JsonBackReference
	private Customer customer;
	
//	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "videoCopy_id")
	private VideoCopy videoCopy;
	
	public Rental(String rentalDate, String returnDate) {
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
	}
}
