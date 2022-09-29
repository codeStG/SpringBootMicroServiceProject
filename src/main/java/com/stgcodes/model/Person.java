package com.stgcodes.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {

    private String firstName;
    private String lastName;
    private String username;
    private String dateOfBirth;
    private String socialSecurityNumber;
    private String gender;
    private String email;
}
