DROP TABLE IF EXISTS "movie_countries";

CREATE TABLE "movie_countries" (
    id SERIAL PRIMARY KEY,
    country_id INTEGER NOT NULL,
    movie_id INTEGER NOT NULL
);
