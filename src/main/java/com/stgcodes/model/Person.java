package com.stgcodes.model;

import com.stgcodes.entity.PhoneEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Person {

    private String personId;
    private String firstName;
    private String lastName;
    private String username;
    private String dateOfBirth;
    private String socialSecurityNumber;
    private String gender;
    private String email;
    private List<PhoneEntity> phones;
}
