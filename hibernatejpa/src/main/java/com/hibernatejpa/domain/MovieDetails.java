package com.hibernatejpa.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie_details")
public class MovieDetails {
	
	// n'existe pas en BDD  donc pas de @GeneratedValue
	@Id
	private Long id;
	
	@Column(length = 400)
	private String plot;
	
	@OneToOne
	@MapsId
	private Movie movie;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlot() {
		return plot;
	}

	public MovieDetails setPlot(String plot) {
		this.plot = plot;
		return this;
	}

	public Movie getMovie() {
		return movie;
	}

	public MovieDetails setMovie(Movie movie) {
		this.movie = movie;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(58);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if(!(obj instanceof MovieDetails)) {
			return false;
		}
		MovieDetails other = (MovieDetails) obj;
		if(id == null && other.getId() == null) {
			return Objects.equals(plot, other.getPlot());
		}
		return id != null && Objects.equals(id, other.getId());
	}
	
	
}
