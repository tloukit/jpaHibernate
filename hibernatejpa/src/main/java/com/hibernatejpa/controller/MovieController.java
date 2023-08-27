package com.hibernatejpa.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hibernatejpa.domain.Movie;
import com.hibernatejpa.repository.MovieRepository;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	private MovieRepository repository;
	
	@PostMapping("/")
	public Movie create(@RequestBody Movie movie) {
		repository.persist(movie);
		return movie;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Movie> get(@PathVariable("id") Long id) {
		Movie movie =  repository.find(id);
		if(Objects.isNull(movie)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(movie);
	}
	
	@PutMapping("/")
	public ResponseEntity<Movie> update(@RequestBody Movie movie) {
		Optional<Movie> res = repository.update(movie);
		if(res.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(res.get());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Movie> delete(@PathVariable("id") Long id) {
		boolean removed = repository.remove(id);
		return removed ?  ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
	
//	@PutMapping("/")
//	public Movie update(@RequestBody Movie movie) {
//		return repository.merge(movie);
//	}
}
