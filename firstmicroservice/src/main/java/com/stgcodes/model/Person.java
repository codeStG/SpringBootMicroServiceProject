package com.stgcodes.model;

import lombok.Data;

import java.util.Date;

@Data
public class Person {

    private String firstName;
    private String lastName;
    private String username;
    private String dateOfBirth;
    private String socialSecurityNumber;
    private String gender;
    private String email;

    public Person(String firstName, String lastName, String username, String dateOfBirth, String socialSecurityNumber, String gender, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.socialSecurityNumber = socialSecurityNumber;
        this.gender = gender;
        this.email = email;
    }
}
