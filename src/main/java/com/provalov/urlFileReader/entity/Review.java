package com.provalov.urlFileReader.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Viacheslav Provalov
 */
@Data
@Builder
public class Review {
    @EqualsAndHashCode.Exclude
    private int id;
    private int movieId;
    private int userId;
    private String review;
    private double rating;
}
