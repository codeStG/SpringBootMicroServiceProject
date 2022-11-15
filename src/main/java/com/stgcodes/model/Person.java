package com.stgcodes.model;

import java.time.LocalDate;
import java.util.List;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.validation.enums.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {

    private Long personId;
    private String firstName;
    private String lastName;
    private String username;
    private LocalDate dateOfBirth;
    private int age;
    private String socialSecurityNumber;
    private Gender gender;
    private String email;
    private List<PhoneEntity> phones;
}
