package com.hibernatejpa.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_generator")
	@SequenceGenerator(name = "review_generator", sequenceName ="review_seq", allocationSize = 1) 
	private Long id;
	
	private String author;
	
	private String content;
	
	@ManyToOne
	@JoinColumn(name="movie_id")
	private Movie movie;
	
	@Min(value=0)
	@Max(value=10)
	private Integer rating;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public Review setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Review setContent(String content) {
		this.content = content;
		return this;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	
	
	public Integer getRating() {
		return rating;
	}

	public Review setRating(Integer rating) {
		this.rating = rating;
		return this;	
	}

	@Override
	public int hashCode() {
		return Objects.hash(31);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Review)) {
			return false;
		}
		Review other = (Review) obj; 
		if(id == null && other.getId() == null) {
			return Objects.equals(author, other.getAuthor())
					&& Objects.equals(content, other.getContent())
					&& Objects.equals(rating, other.getRating());
		}
		return id != null && Objects.equals(id, other.id);
	}
	 // IMPORTANT : on ne met pas Movie ni dans hashcode, equals , toString car Movie ici est une Association
	@Override
	public String toString() {
		return "Review [id=" + id + ", author=" + author + ", content=" + content + ", rating=" + rating  + "]";
	}

}