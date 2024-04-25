package com.casumo.videorental.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name = "Customers")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Firstname must not be empty nor containing blank spaces!")
	@Column(name = "First_Name")
	private String firstName;
	@NotBlank(message = "Lastname must not be empty nor containing blank spaces!")
	@Column(name = "Last_Name")
	private String lastName;
	@Email(message = "Email must be in the format something@email")
	@Column(name = "Email")
	private String email;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JsonManagedReference
	private List<Rental> rentals;
	
	public void addRental(Rental rental) {
        rentals.add(rental);
        rental.setCustomer(this);
    }
	
	@Override
	public String toString() {
		return "%d %s".formatted(id, firstName);
	}
}