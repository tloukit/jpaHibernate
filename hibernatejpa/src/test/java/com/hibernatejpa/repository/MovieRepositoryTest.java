package com.hibernatejpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Set;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hibernatejpa.config.PersistenceConfig;
import com.hibernatejpa.domain.Award;
//import com.hibernatejpa.config.PersistenceConfigTest;
import com.hibernatejpa.domain.Certification;
import com.hibernatejpa.domain.Genre;
import com.hibernatejpa.domain.Movie;
import com.hibernatejpa.domain.MovieDetails;
import com.hibernatejpa.domain.Review;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

//@ContextConfiguration(classes = { PersistenceConfigTest.class })
//@SqlConfig(dataSource = "dataSourceH2", transactionManager = "transactionManager")

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@SqlConfig(dataSource = "dataSource", transactionManager = "transactionManager")
@Sql({ "/datas/datas-test.sql" })
public class MovieRepositoryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepositoryTest.class);
	@Autowired
	public MovieRepository repository;

	@Test
	public void Review_ratingValidation() {
		Movie movie = new Movie().setName("Fight Club").setCertification(Certification.INTERDIT_MOINS_12)
				.setDescription("le fight club n'existe pas");
		
		Review review1 = new Review().setAuthor("max").setContent("ça passe, sans plus").setRating(6); // 11 pour ko
		movie.addReview(review1);
		// movie est en cascade => sauve les reviews
		repository.persist(movie);
	}
	
	@Test
	public void save_withAwards() {
		Movie movie = new Movie().setName("La leçon de piano").setCertification(Certification.TOUS_PUBLIC)
				.setDescription("une jeune femme décide d'apprendre le piano");
		
		Review review1 = new Review().setAuthor("toufik").setContent("beau film").setRating(8); // 11 pour ko
		Award award1 = new Award().setName("prix du meilleur second rôle").setYear(1993);
		Award award2 = new Award().setName("prix de la meilleure bande son").setYear(1993);
		movie.addReview(review1);
		movie.addAward(award1);
		movie.addAward(award2);
		repository.persist(movie);

		assertThat(award1.getId()).as("l' award1 n'a pas été enregistré").isNotNull();
		assertThat(award2.getId()).as("l' award2 n'a pas été enregistré").isNotNull();
		assertThat(movie.getAwards()).as("les awards n'ont pas été enregistré").hasSize(2);
	}
	
	@Test
	public void Review_ratingValidationWithoutMovie() {
		Review review1 = new Review().setAuthor("max").setContent("ça passe, sans plus").setRating(12);
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Review>> errors = validator.validate(review1);
		assertThat(errors).as("le rating aurait dû provoquer une erreur").hasSize(1);
	}
	
	@Test
	public void save_casNominal() {
		Movie movie = new Movie().setName("Inception")
				.setCertification(Certification.INTERDIT_MOINS_12)
				.setDescription("test");
		
//		movie.setName("Inception");
//		movie.setCertification(Certification.INTERDIT_MOINS_12);
		repository.persist(movie);
	}
	
	@Test
	public void save_withGenres() {
		Movie movie = new Movie().setName("The Big Bang Theory");
		Genre bio = new Genre().setNom("Biography");
		Genre drama = new Genre().setNom("Drama");
		movie.addGenre(bio).addGenre(drama);
		
		repository.persist(movie);

		assertThat(bio.getId()).as("l'entité genre bio n'a pas été persisté").isNotNull();
		assertThat(drama.getId()).as("l'entité genre drama n'a pas été persisté").isNotNull();
	}
	
	@Test
	public void save_withExistingGenre() {
		Movie movie = new Movie().setName("The Big Bang Theory");
		Genre bio = new Genre().setNom("Biography");
		Genre drama = new Genre().setNom("Drama");
		Genre action = new Genre().setNom("Action");
		action.setId(-1L); // le fait d'affecter un id simule que ce genre est detached
		movie.addGenre(bio).addGenre(drama).addGenre(action);
		repository.merge(movie);
	}
	
	@Test
	public void associationSave_casNominal() {
		Movie movie = new Movie().setName("Fight Club").setCertification(Certification.INTERDIT_MOINS_12)
				.setDescription("le fight club n'existe pas");
		
		Review review1 = new Review().setAuthor("max").setContent("film vite fait");
		Review review2 = new Review().setAuthor("farid").setContent("la bagarre à son meilleur");
		
		movie.addReview(review1); // méthodé qui centralise : review1.setMovie(movie) et movie.getReviews().add(review_X)
		movie.addReview(review2);
		
//		review1.setMovie(movie); // permet d"insérer la FK dans tbl review
//		review1.setMovie(movie);
//		movie.getReviews().add(review1);
//		movie.getReviews().add(review2);
		
		// movie est en cascade => sauve les reviews
		repository.persist(movie);
	}
	
	@Test
	public void addMovieDetails_casNominal() {
		MovieDetails movieDetails = new MovieDetails().setPlot("intrigue du film traine en longueur");
		repository.addMovieDetails(movieDetails, -2L);
		assertThat(movieDetails.getId()).as("details du film non enregistrés").isNotNull();
		
		
	}
	
	@Test
	public void associationGet_casNominal() {
		assertThrows(LazyInitializationException.class, () ->  {
			Movie movie = repository.find(-1L);
			LOGGER.trace("nb de reviews: " + movie.getReviews().size());
		});
	}

	@Test
	public void merge_casSimule() {
		Movie movie = new Movie();
		movie.setName("Inception 2");
		movie.setId(-1L);
		Movie mergedMovie = repository.merge(movie);
		assertThat(mergedMovie.getName()).as("le nom du film n'a pas été mis à jour").isEqualTo("Inception 2");

	}

	@Test
	public void find_casNominal() {
		Movie memento = repository.find(-2L);
		assertThat(memento.getName()).as("mauvais film récupéré").isEqualTo("Memento");
		assertThat(memento.getCertification()).as("le converter  n'a pas fonctionné")
				.isEqualTo(Certification.INTERDIT_MOINS_12);
	}

	@Test
	public void getAll_casNominal() {
		List<Movie> movies = repository.getAll();
		assertThat(movies).as("l'ensemble des films n'a pas été récupéré").hasSize(2);
	}

	@Test
	public void remove_casNominal() {
		repository.remove(-2L);
	}

	@Test
	public void getReference_casNominal() {
		Movie movie = repository.getReference(-2L);
		assertThat(movie.getId()).as("la référence n'a pas été correctement chargé").isEqualTo(-2L);
	}

	@Test
	public void getReference_fail() {
		assertThrows(LazyInitializationException.class, () -> {
			Movie movie = repository.getReference(-2L);
			LOGGER.trace("movie name : " + movie.getName());
			assertThat(movie.getId()).as("la référence n'a pas été correctement chargé").isEqualTo(-2L);
		});
	}
	
	@Test
	public void addReview() {
		Movie movie = repository.getReference(-1L);
		repository.addMovie(movie, new Review().setAuthor("toufik").setContent("cool"));
	}
	
	@Test
	public void removeGenre() {	
		Movie movie = repository.getReference(-1L);
		repository.removeGenre(movie, new Genre("Action"));
	}
}




