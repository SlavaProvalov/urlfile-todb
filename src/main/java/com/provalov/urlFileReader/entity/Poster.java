package com.provalov.urlFileReader.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Viacheslav Provalov
 */
@Data
@Builder
public class Poster {
    private int id;
    private int movieId;
    private String posterLink;
}
