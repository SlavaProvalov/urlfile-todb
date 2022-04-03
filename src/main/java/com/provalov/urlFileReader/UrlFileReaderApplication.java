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
    }

}
