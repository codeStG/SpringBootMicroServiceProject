package com.stgcodes.model;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.validation.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class User {

    private Long userId;
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
