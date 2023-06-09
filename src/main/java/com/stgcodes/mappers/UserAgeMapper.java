package com.stgcodes.mappers;

import java.time.LocalDate;

public class UserAgeMapper {

    public int getAge(LocalDate dob) {
        if(dob == null) {
            return 0;
        }
        return LocalDate.now().getYear() - dob.getYear();
    }
}
