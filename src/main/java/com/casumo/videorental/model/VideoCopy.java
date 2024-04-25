package com.casumo.videorental.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "VideoCopies")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class VideoCopy {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//	@JsonManagedReference
	@OneToMany(mappedBy = "videoCopy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Rental> rentals;
	
    @ManyToOne
    @JoinColumn(name = "video_id")
//    @JsonBackReference
    private Video video;
    @NotBlank(message = "This field must not be empty nor containing blank spaces!")
    private String available;
    
    public VideoCopy(Long id, String available) {
    	this.id = id;
    	this.available = available;
    }
    
    public void addRental(Rental rental) {
        rentals.add(rental);
        rental.setVideoCopy(this);
    }
    
    @Override
    public String toString() {
    	return "%d %s".formatted(id, available);
    }
}
