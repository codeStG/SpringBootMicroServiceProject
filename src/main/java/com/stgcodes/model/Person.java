package com.stgcodes.model;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.validation.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Person {

    private Long personId;
    private String firstName;
    private String lastName;
    private String username;
    private String dateOfBirth;
    private int age;
    private String socialSecurityNumber;
    private Gender gender;
    private String email;
    private List<PhoneEntity> phones;
}
