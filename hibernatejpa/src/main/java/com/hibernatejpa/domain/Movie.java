package com.hibernatejpa.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Movie {
	
//	public enum Certification {
//		TOUS_PUBLIC, INTERDIT_MOINS_12, INTERDIT_MOINS_16, INTERDIT_MOINS_18
//	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_generator")
	@SequenceGenerator(name = "movie_generator", sequenceName ="movie_seq", allocationSize = 1) 
	private Long id;
	
	private String name;
	
	private String description;
	
//	@Enumerated(EnumType.STRING)
	private Certification certification;
	
	// indique que dans review l'attribut movie correspond à la FK
	// review est donc le propriétaire de la relation
	@OneToMany(cascade = CascadeType.ALL , orphanRemoval = true, mappedBy = "movie")
	private List<Review> reviews = new ArrayList<Review>();
//	private Set<Review> reviews = new HashSet<>(); // test du Set
	
	// @JoinTable => table de jointure à qui ,où spécifie les deux colonnes
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name="movie_id"), inverseJoinColumns = @JoinColumn(name="genre_id") )
	private Set<Genre> genres = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL , orphanRemoval = true, mappedBy = "movie")
	private List<Award> awards = new ArrayList<Award>(); 
	
	public Movie addGenre(Genre genre) {
		if(genre != null) {
			this.genres.add(genre);
			genre.getMovies().add(this);
		}
		return this;
	}
	
	public Movie removeGenre(Genre genre) {
		if(genre != null) {
			this.genres.remove(genre);
			genre.getMovies().remove(this);
		}
		return this;
	}
	
	public Movie addReview(Review review) {
		if(review != null) {
			this.reviews.add(review);
			review.setMovie(this);
		}
		return this;
	}
	
	public Movie removeReview(Review review) {
		if(review != null) {
			this.reviews.remove(review);
			review.setMovie(null);
		}
		return this;
	}
	
	public Movie addAward(Award award) {
		if(award != null) {
			this.awards.add(award);
			award.setMovie(this);
		}
		return this;
	}
	
	public Movie removeAward(Award award) {
		if(award != null) {
			this.awards.remove(award);
			award.setMovie(null);
		}
		return this;
	}
	
	public Set<Genre> getGenres() {
		return Collections.unmodifiableSet(genres);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public Movie setName(String name) {
		this.name = name;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Movie setDescription(String description) {
		this.description = description;
		return this;
	}
	public Certification getCertification() {
		return certification;
	}
	public Movie setCertification(Certification certification) {
		this.certification = certification;
		return this;
	}
	
	public List<Review> getReviews() {
		// pour empêcher d'ajouter une review en faisant getReviews().add(review); si on essaie => RunTimeException()
		// et forcer l'utilisation de addReview(review);
		return Collections.unmodifiableList(reviews);
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	/* public Set<Review> getReviews() {
		// pour empêcher d'ajouter une review en faisant getReviews().add(review); si on essaie => RunTimeException()
		// et forcer l'utilisation de addReview(review);
		return Collections.unmodifiableSet(reviews);
	}
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	} */
	
	public List<Award> getAwards() {
		return Collections.unmodifiableList(awards);
	}

	// cas : pas d'id fonctionnel
	@Override
	public int hashCode() {
		return Objects.hash(31);
	}
	
	// implémentation de equals sans id fonctionnel (ce qui est le cas pour Movie)
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if(!(obj instanceof Movie)) {
				return false;
			}
			Movie other = (Movie) obj;
			if(id == null && other.getId() == null) {
				return Objects.equals(name, other.getName())
						&& Objects.equals(description, other.getDescription())
						&& Objects.equals(certification, other.getCertification());
			}
			return id != null && Objects.equals(id, other.getId());
		}
		@Override
		public String toString() {
			return "Movie [id=" + id + ", name=" + name + ", description=" + description + ", certification="
					+ certification + "]";
		}
	
		
		
/*	// implémentation de equals en considérant le "name" comme un id fonctionnel
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if(!(obj instanceof Movie)) {
			return false;
		}
		Movie other = (Movie) obj;
		return Objects.equals(name, other.name);
	}
		@Override
		public int hashCode() {
			return Objects.hash(name);
		}
	*/
	
}

