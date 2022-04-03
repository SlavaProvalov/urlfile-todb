DROP TABLE IF EXISTS "movies";

CREATE TABLE "movies" (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    original_name varchar(255) NOT NULL,
    price NUMERIC(10,2),
    production_year NUMERIC(4) NOT NULL,
    description varchar(1024)
);

DROP TABLE IF EXISTS "movie_posters";

CREATE TABLE "movie_posters" (
    id SERIAL PRIMARY KEY,
    movie_id INTEGER NOT NULL,
    poster_link varchar(2048)
);

DROP TABLE IF EXISTS "genres";

CREATE TABLE "genres" (
    id SERIAL PRIMARY KEY,
    genre_name varchar(50) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS "movie_genres";

CREATE TABLE "movie_genres" (
    id SERIAL PRIMARY KEY,
    genre_id INTEGER NOT NULL,
    movie_id INTEGER NOT NULL
);

DROP TABLE IF EXISTS "reviews";

CREATE TABLE "reviews" (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    movie_id INTEGER NOT NULL,
    review varchar(500),
    rating NUMERIC(1)
);

DROP TABLE IF EXISTS "countries";

CREATE TABLE "countries" (
    id SERIAL PRIMARY KEY,
    country varchar(50)
);

DROP TABLE IF EXISTS "users";

CREATE TABLE "users" (
    id SERIAL PRIMARY KEY,
    login varchar(50) UNIQUE NOT NULL,
    password varchar(65) NOT NULL,
    role varchar(10) NOT NULL
);