package com.stgcodes.criteria;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonCriteria {
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
}
