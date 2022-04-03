package com.provalov.urlFileReader.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Viacheslav Provalov
 */
@Data
@Builder
public class User {
    private int id;
    private String login;
    private String password;
    private String role;
    private String userName;
}
