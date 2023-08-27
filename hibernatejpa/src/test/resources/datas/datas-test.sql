--SET REFERENTIAL_INTEGRITY FALSE;
--truncate table Movie;
--truncate table Movie_Genre;
--truncate table Review;
--truncate table Genre;
--truncate table Movie_Details;
--SET REFERENTIAL_INTEGRITY TRUE;

truncate table Movie, Genre, Review, Movie_Genre, Movie_Details, Award;

insert into Movie (name,certification, id) values('Inception', 1 , -1);
insert into Movie (name,certification, id) values('Memento', 2 ,-2);

insert into Review (author,content, movie_id, id) values('max', 'pas mal', -1 ,-1);
insert into Review (author,content, movie_id, id) values('bill', 'cool de fou', -1 ,-2);

insert into Genre (name,id) values ('Action', -1);
insert into Genre(name, id) values('Sci-Fi',-2);

insert into Movie_Genre(genre_id, movie_id) values(-1,-1);
insert into Movie_Genre(genre_id, movie_id) values(-2,-1);
