package com.hibernatejpa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hibernatejpa.config.PersistenceConfig;
import com.hibernatejpa.repository.MovieRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@SqlConfig(dataSource = "dataSource", transactionManager = "transactionManager")
@Sql({ "/datas/datas-test.sql" })
public class EqualsHashcodeTest {
	
	@Autowired
	private MovieRepository repository;
	
	@Test
	public void equalsTest() {
		Movie movie = new Movie().setName("Dune");
		repository.persist(movie);
		
		Movie movieBDD = repository.find(movie.getId());
		assertThat(movie.equals(movieBDD)).as("il devrait y avoir une égalité entre ces 2 entités").isTrue();
		
		Movie movieRef = repository.getReference(movie.getId());
		assertThat(movie.equals(movieRef)).as("il devrait y avoir une égalité entre ces 2 entités").isTrue();
	}

	@Test
	public void setTest() {
		Movie movie = new Movie().setName("Dune");
		Set<Movie> setMovies = new HashSet<>();
		setMovies.add(movie);
		assertThat(setMovies.contains(movie)).as("le set devrait contenir le movie").isTrue();
		repository.persist(movie);
		assertThat(setMovies.contains(movie)).as("le set devrait contenir le movie une fois persisté").isTrue();		
	}
}
