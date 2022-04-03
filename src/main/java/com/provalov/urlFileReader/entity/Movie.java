package com.provalov.urlFileReader.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Viacheslav Provalov
 */

@Data
@Builder
public class Movie {
    @EqualsAndHashCode.Exclude
    private int id;

    private String name;
    private String originalName;
    private int productionYear;

    @EqualsAndHashCode.Exclude
    private String description;
    @EqualsAndHashCode.Exclude
    private double price;
    @EqualsAndHashCode.Exclude
    private double rating;
    @EqualsAndHashCode.Exclude
    private List<Country> countries = new ArrayList<>();
    @EqualsAndHashCode.Exclude
    private List<Genre> genres = new ArrayList<>();

    public void addCounty(Country country) {
        countries.add(country);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }
}
