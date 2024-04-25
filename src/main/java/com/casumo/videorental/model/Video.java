package com.casumo.videorental.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

//import org.hibernate.annotations.CascadeType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Videos")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Video {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	@NotBlank(message = "video Title must not be empty nor containing blank spaces!")
	@Column(name = "Video_Title")
    private String videoTitle;
	@Column(name = "Director")
	private String director;
    @Column(name = "Video_Rental_Price")
    private double videoRentalPrice;
    @Positive(message = "Price must be positive")
    @NotNull(message = "Publish year must not be null")
    @Column(name = "Publishing_Year")
    private Integer year;
    
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<VideoCopy> videoCopies;

    @Override
    public String toString() {
    	return "%d %s".formatted(id, videoTitle);
    }
}
