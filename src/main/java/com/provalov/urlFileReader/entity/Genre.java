package com.provalov.urlFileReader.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Viacheslav Provalov
 */

@Data
@Builder
public class Genre {
    @EqualsAndHashCode.Exclude
    private int id;
    private String genreName;

}
