package com.hibernatejpa.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Award {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "award_generator")
	@SequenceGenerator(name = "award_generator", sequenceName ="award_seq", allocationSize = 1) 
	private Long id;
	
	private String name;
	
	private Integer year;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	private Movie movie;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Award setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getYear() {
		return year;
	}

	public Award setYear(Integer year) {
		this.year = year;
		return this;
	}

	public Movie getMovie() {
		return movie;
	}

	public Award setMovie(Movie movie) {
		this.movie = movie;
		return this;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(62);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Award)) {
			return false;
		}
		Award other = (Award) obj; 
		if(id == null && other.getId() == null) {
			return Objects.equals(name, other.getName())
					&& Objects.equals(year, other.getYear());
		}
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Award [id=" + id + ", name=" + name + ", year=" + year + ", movie=" + movie + "]";
	}
}
