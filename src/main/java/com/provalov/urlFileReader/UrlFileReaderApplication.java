package com.provalov.urlFileReader;

import com.provalov.urlFileReader.util.Processor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class UrlFileReaderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(UrlFileReaderApplication.class, args);
        Processor processor = applicationContext.getBean(Processor.class);
        processor.processGenres();
        processor.processCountries();
        processor.processMovies();
        processor.processPosters();
        processor.processUsers();
        processor.processReviews();
        processor.processRelations();
//        List<String> movies = Processor.parseMovies();
//        List<String> posters = Processor.parsePosters();
//        List<String> users = Processor.parseUsers();
//        List<String> reviews = Processor.parseReviews();
//
//        System.out.println("--------------------------------- GENRES ------------------------------");
//        genres.forEach(System.out::println);
//        System.out.println("--------------------------------- MOVIES ------------------------------");
//        movies.forEach(System.out::println);
//        System.out.println("--------------------------------- POSTERS ------------------------------");
//        posters.forEach(System.out::println);
//        System.out.println("--------------------------------- USERS ------------------------------");
//        users.forEach(System.out::println);
//        System.out.println("--------------------------------- REVIEWS ------------------------------");
//        reviews.forEach(System.out::println);

    }

}
