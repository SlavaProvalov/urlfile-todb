ALTER TABLE "movie_posters"
ADD UNIQUE (movie_id);

ALTER TABLE "countries"
ADD UNIQUE (country);

ALTER TABLE "users"
ALTER COLUMN login SET DATA TYPE varchar(320);

CREATE UNIQUE INDEX idx_movie_genres
ON "movie_genres" (movie_id, genre_id);

CREATE UNIQUE INDEX idx_user_movies
ON "reviews" (user_id, movie_id);

CREATE UNIQUE INDEX idx_movie_countries
ON "movie_countries" (movie_id, country_id);

