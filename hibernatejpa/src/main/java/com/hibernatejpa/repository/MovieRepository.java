package com.hibernatejpa.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hibernatejpa.domain.Genre;
import com.hibernatejpa.domain.Movie;
import com.hibernatejpa.domain.MovieDetails;
import com.hibernatejpa.domain.Review;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class MovieRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class);
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public void persist(Movie movie) {
		entityManager.persist(movie);
		LOGGER.trace("entityManager.contains() : " + entityManager.contains(movie)); 
//		entityManager.detach(movie);
		LOGGER.trace("entityManager.contains() : " + entityManager.contains(movie));
	}
	@Transactional
	public void addMovieDetails(MovieDetails movieDetails, Long idMovie) {
		Movie movieRef = getReference(idMovie);
		movieDetails.setMovie(movieRef);
		entityManager.persist(movieDetails);
	}

	@Transactional
	public void addMovie(Movie movie, Review review) {
		Movie movieBD = find(movie.getId());
		movieBD.addReview(review);
	}
	
	@Transactional
	public void removeGenre(Movie movie, Genre genre) {
		Movie movieBD = find(movie.getId());
		movieBD.removeGenre(genre);
	}
	
	public Movie find(Long id) {
		Movie result =  entityManager.find(Movie.class, id);
		LOGGER.trace("entityManager.contains() : " + entityManager.contains(result)); 
		return result;
	}
	
	public List<Movie> getAll(){
		List<Movie> movies=  entityManager.createQuery("from Movie", Movie.class).getResultList();
		LOGGER.warn("entityManager.contains() : " + entityManager.contains(movies.get(0))); 
		return movies;
	}
	
	@Transactional
	public Movie merge(Movie movie) {
		//entityManager commence par récupérer en BD la version de cette entité 
		// EM ne prend pas celle qu'on lui passe en param
		// s'il voit une diff entre movie BD et movie param =>fait un update si besoin
		 entityManager.merge(movie);
		 Movie res = entityManager.find(Movie.class, movie.getId());
		 return res;
	}
	
	@Transactional
	public Optional<Movie> update(Movie movie) {
		if(Objects.isNull(movie.getId())) {
			return Optional.empty();
		}
		Movie movieBD = entityManager.find(Movie.class, movie.getId());
		if(!Objects.isNull(movieBD)) {
			movieBD.setName(movie.getName());
			movieBD.setDescription(movie.getDescription());
		}
		return Optional.ofNullable(movieBD);
	}
	
//	@Transactional
//	public delete
	
	@Transactional
	public boolean remove(Long id) {
		if(!Objects.isNull(id)) {
			Movie movie = entityManager.find(Movie.class, id);
			if(!Objects.isNull(movie)) {
				entityManager.remove(movie);
				return true;
			}
		}
		return false;
	}
	
//	@Transactional
	public Movie getReference(Long id) {
		// récupère l'id (référence) de l'entité en Base de données. C'est la  notion de proxy hibernate
		// les proxies sont des références vers des entités en Base et chargeables à la dde à condition que la session soit ouverte
		Movie movie = entityManager.getReference(Movie.class, id);
//		LOGGER.trace("chargement movie : " + movie.getName());
		return movie;
	}
	
	
	
	

}
