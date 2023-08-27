package com.hibernatejpa.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Genre {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_generator")
	@SequenceGenerator(name = "genre_generator", sequenceName ="genre_seq", allocationSize = 1) 
	private Long id;
	
	// nom est ici un identifiant fontionnel
	// il est unique et ne changera pas (immuable)
	private String name;
	
	// Movie est responsable de la relation
	@ManyToMany(mappedBy = "genres")
	private Set<Movie> movies = new HashSet<>();
	
	
	public Genre() {}

	public Genre(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return name;
	}

	public Genre setNom(String nom) {
		this.name = nom;
		return this;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if(!(obj instanceof Genre)) {
			return false;
		}
		Genre other = (Genre) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Genre [id=" + id + ", nom=" + name + "]";
	}
	
	
	
}
