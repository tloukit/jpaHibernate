package com.hibernatejpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibernatejpa.domain.Movie;
import com.hibernatejpa.repository.MovieRepository;

import jakarta.transaction.Transactional;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository repository;
	
	@Transactional
	public void updateDescription(Long id, String description) {
		Movie movie = repository.find(id);
		movie.setDescription(description);
	}

}
