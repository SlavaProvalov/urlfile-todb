package com.provalov.urlFileReader.util;

import com.provalov.urlFileReader.data.JdbcDao;
import com.provalov.urlFileReader.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.provalov.urlFileReader.util.EntityReader.readUFromUrl;

/**
 * @author Viacheslav Provalov
 */

@Service
public class Processor {
    private static final String GENRES = "genres";
    private static final String MOVIES = "movies";
    private static final String POSTERS = "movie_posters";
    private static final String USERS = "users";
    private static final String REVIEWS = "reviews";
    private static final String COUNTRIES = "countries";

    private static final List<String> REVIEWS_COLUMN_NAMES = List.of("movie_id", "user_id", "review");
    private static final List<String> USERS_COLUMN_NAMES = List.of("user_name", "login", "password", "role");
    private static final List<String> POSTERS_COLUMN_NAMES = List.of("movie_id", "poster_link");
    private static final List<String> COUNTRIES_COLUMN_NAMES = List.of("country");
    private static final List<String> MOVIES_COLUMN_NAMES = List.of("name", "original_name", "price", "production_year", "description", "rating");
    private static final List<String> GENRES_COLUMN_NAMES = List.of("genre_name");
    private static final List<String> MV_CNTR_COLUMN_NAMES = List.of("movie_id", "country_id");
    private static final List<String> MV_GNR_COLUMN_NAMES = List.of("movie_id", "genre_id");


    private static final String MOVIE_COUNTRIES = "movie_countries";
    private static final String MOVIE_GENRES = "movie_genres";
    private final Set<String> countriesSet = new LinkedHashSet<>();

    private final Map<String, Movie> movies = new LinkedHashMap<>();
    private final Map<String, Genre> genres = new HashMap<>();
    private final Map<String, Country> countries = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();

    private final Map<String, List<Integer>> movie_countries = new HashMap<>();
    private final Map<String, List<Integer>> movie_genres = new HashMap<>();

    private final JdbcDao jdbcDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Processor(JdbcDao jdbcDao, PasswordEncoder passwordEncoder) {
        this.jdbcDao = jdbcDao;
        this.passwordEncoder = passwordEncoder;
    }


    public void processReviews() {
        System.out.println("------------------------ processReviews START ----------------------------------- ");
        String fileName = "https://trello.com/1/cards/5c7d3c9c8d6ddf776c2d3dde/attachments/5c7d3c9d8d6ddf776c2d3e0f/download/review.txt";
        List<String> strings = readUFromUrl(fileName);
        List<List<String>> rowsValues = new ArrayList<>();
        List<String> row = null;

        for (int i = 0; i < strings.size(); i++) {
            if (i % 3 == 0) {
                // movie_id
                row = new ArrayList<>();
                row.add(String.valueOf(movies.get(strings.get(i).trim()).getId()));
            } else if (i % 3 == 1) {
                // user_name
                row.add(String.valueOf(users.get(strings.get(i).trim()).getId()));
            } else {
                // review
                row.add(strings.get(i).trim());
                rowsValues.add(row);
            }
        }

        try {
            jdbcDao.insert(REVIEWS, REVIEWS_COLUMN_NAMES, rowsValues);
            System.out.println("SUCCESS!");
        } catch (DataAccessException e) {
            System.out.println("Table " + REVIEWS + " processed with error. Error message: " + e.getMessage());
        }

    }

    public void processUsers() {
        System.out.println("------------------------ processUsers START ----------------------------------- ");
        String fileName = "https://trello.com/1/cards/5c7d3c9c8d6ddf776c2d3dde/attachments/5c7d3c9d8d6ddf776c2d3e11/download/user.txt";
        List<String> strings = readUFromUrl(fileName);
        List<List<String>> rowsValues = new ArrayList<>();
        List<String> row = null;
        for (int i = 0; i < strings.size(); i++) {
            if (i % 3 == 0) {
                // user_name
                row = new ArrayList<>();
                row.add(strings.get(i).trim());
            } else if (i % 3 == 1) {
                // login
                row.add(strings.get(i).trim());
            } else {
                // password
                row.add(passwordEncoder.encode(strings.get(i).trim()));
                row.add("USER");
                rowsValues.add(row);
            }
        }

        try {
            jdbcDao.insert(USERS, USERS_COLUMN_NAMES, rowsValues);
            System.out.println("SUCCESS!");
        } catch (DataAccessException e) {
            System.out.println("Table " + USERS + " processed with error. Error message: " + e.getMessage());
        }

        List<User> userList = jdbcDao.getAll(User.class, USERS, USERS_COLUMN_NAMES);
        userList.forEach(usr -> users.put(usr.getUserName(), usr));
    }

    public void processPosters() {
        System.out.println("------------------------ processPosters START ----------------------------------- ");
        String fileName = "https://trello.com/1/cards/5c7d3c9c8d6ddf776c2d3dde/attachments/5c7d3c9d8d6ddf776c2d3e0d/download/poster.txt";
        List<String> rows = readUFromUrl(fileName);

        List<List<String>> rowsValues = new ArrayList<>();

        for (String row : rows) {
            String[] split = row.split(" https:");
            rowsValues.add(List.of(String.valueOf(movies.get(split[0].trim()).getId()), "https:" + split[1].trim()));
        }

        try {
            jdbcDao.insert(POSTERS, POSTERS_COLUMN_NAMES, rowsValues);
            System.out.println("SUCCESS!");
        } catch (DataAccessException e) {
            System.out.println("Table " + POSTERS + " processed with error. Error message: " + e.getMessage());
        }
    }

    public void processCountries() {
        System.out.println("------------------------ processCountries START ----------------------------------- ");
        String fileName = "https://trello.com/1/cards/5c7d3c9c8d6ddf776c2d3dde/attachments/5c7d3c9d8d6ddf776c2d3e0b/download/movie.txt";
        List<String> rows = readUFromUrl(fileName);

        for (int i = 0; i < rows.size(); i++) {
            if (i % 7 == 2) {
                // Countries
                String s = rows.get(i);
                String[] c = s.split(",");
                Arrays.stream(c).map(String::trim).forEach(countriesSet::add);
            }
        }
        List<List<String>> rowsValues = new ArrayList<>();
        for (String country : countriesSet) {
            rowsValues.add(List.of(country));
        }
        try {
            jdbcDao.insert(COUNTRIES, COUNTRIES_COLUMN_NAMES, rowsValues);
            System.out.println("SUCCESS!");
        } catch (DataAccessException e) {
            System.out.println("Table " + COUNTRIES + " processed with error. Error message: " + e.getMessage());
        }

        List<Country> countryList = jdbcDao.getAll(Country.class, COUNTRIES, COUNTRIES_COLUMN_NAMES);
        countryList.forEach(cntry -> countries.put(cntry.getCountry(), cntry));
    }

    public void processMovies() {
        System.out.println("------------------------ processMovies START ----------------------------------- ");
        String fileName = "https://trello.com/1/cards/5c7d3c9c8d6ddf776c2d3dde/attachments/5c7d3c9d8d6ddf776c2d3e0b/download/movie.txt";
        List<String> rows = readUFromUrl(fileName);
        List<Movie> newMoviesList = new ArrayList<>();
        Movie newMovie = null;

        for (int i = 0; i < rows.size(); i++) {
            if (i % 7 == 0) {
                // Title
                String[] split = rows.get(i).split("/");
                newMovie = Movie.builder().build();
                newMovie.setName(split[0].trim());
                newMovie.setOriginalName(split[1].trim());

            } else if (i % 7 == 1) {
                // Year
                newMovie.setProductionYear(Integer.parseInt(rows.get(i).trim()));

            } else if (i % 7 == 2) {
                // Countries
                String[] c = rows.get(i).split(",");
                List<Integer> countryIds = Arrays.stream(c)
                        .map(String::trim)
                        .map(countries::get)
                        .map(Country::getId)
                        .collect(Collectors.toList());
                movie_countries.put(newMovie.getName(), countryIds);
            } else if (i % 7 == 3) {
                // Genres
                String[] gens = rows.get(i).split(",");
                List<Integer> genresIds = Arrays.stream(gens)
                        .map(String::trim)
                        .map(genres::get)
                        .map(Genre::getId)
                        .collect(Collectors.toList());
                movie_genres.put(newMovie.getName(), genresIds);
            } else if (i % 7 == 4) {
                // Description
                newMovie.setDescription(rows.get(i).trim());

            } else if (i % 7 == 5) {
                // Rating
                String row = rows.get(i).split(":")[1];
                newMovie.setRating(Double.parseDouble(row.trim()));

            } else {
                // Price
                String row = rows.get(i).split(":")[1];
                newMovie.setPrice(Double.parseDouble(row.trim()));
                newMoviesList.add(newMovie);
            }
        }
        List<List<String>> movieValues = new ArrayList<>();
        for (Movie movie : newMoviesList) {
            movieValues.add(List.of(movie.getName(),
                    movie.getOriginalName(),
                    String.valueOf(movie.getPrice()),
                    String.valueOf(movie.getProductionYear()),
                    movie.getDescription(),
                    String.valueOf(movie.getRating())));
        }

        try {
            jdbcDao.insert(MOVIES, MOVIES_COLUMN_NAMES, movieValues);
            System.out.println("SUCCESS!");
        } catch (DataAccessException e) {
            System.out.println("Table " + MOVIES + " processed with error. Error message: " + e.getMessage());
        }

        List<Movie> movieList = jdbcDao.getAll(Movie.class, MOVIES, MOVIES_COLUMN_NAMES);
        movieList.forEach(mov -> movies.put(mov.getName(), mov));
    }

    public void processGenres() {
        System.out.println("------------------------ processGenres START ----------------------------------- ");
        String fileName = "https://trello.com/1/cards/5c7d3c9c8d6ddf776c2d3dde/attachments/5c7d3c9d8d6ddf776c2d3e09/download/genre.txt";
        List<String> rows = readUFromUrl(fileName);
        List<List<String>> rowsValues = new ArrayList<>();
        for (String row : rows) {
            rowsValues.add(List.of(row.trim()));
        }

        try {
            jdbcDao.insert(GENRES, GENRES_COLUMN_NAMES, rowsValues);
            System.out.println("SUCCESS!");
        } catch (DataAccessException e) {
            System.out.println("Table " + GENRES + " processed with error. Error message: " + e.getMessage());
        }


        List<Genre> genresList = jdbcDao.getAll(Genre.class, GENRES, GENRES_COLUMN_NAMES);
        genresList.forEach(gen -> genres.put(gen.getGenreName(), gen));
    }


    public void processRelations() {
        System.out.println("------------------------ processRelations START ----------------------------------- ");
        List<List<String>> countriesRowsValues = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : movie_countries.entrySet()) {
            List<Integer> countriesIds = entry.getValue();
            for (Integer id : countriesIds) {
                countriesRowsValues.add(List.of(String.valueOf(movies.get(entry.getKey()).getId()), String.valueOf(id)));
            }
        }

        try {
            jdbcDao.insert(MOVIE_COUNTRIES, MV_CNTR_COLUMN_NAMES, countriesRowsValues);
            System.out.println("SUCCESS!");
        } catch (DataAccessException e) {
            System.out.println("Table " + MOVIE_COUNTRIES + " processed with error. Error message: " + e.getMessage());
        }

        List<List<String>> genresRowsValues = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : movie_genres.entrySet()) {
            List<Integer> genreIds = entry.getValue();
            for (Integer id : genreIds) {
                genresRowsValues.add(List.of(String.valueOf(movies.get(entry.getKey()).getId()), String.valueOf(id)));
            }
        }

        try {
            jdbcDao.insert(MOVIE_GENRES, MV_GNR_COLUMN_NAMES, genresRowsValues);
            System.out.println("SUCCESS!");
        } catch (DataAccessException e) {
            System.out.println("Table " + MOVIE_GENRES + " processed with error. Error message: " + e.getMessage());
        }
    }
}
