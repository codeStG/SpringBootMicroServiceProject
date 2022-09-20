package com.stgcodes.validation;

import com.stgcodes.model.Person;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@Slf4j
public class PersonValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        validateFirstName(person, errors);
        validateLastName(person, errors);
        validateUsername(person.getUsername(), errors);
        validateDateOfBirth(person.getDateOfBirth(), errors);
        validateSocialSecurityNumber(errors);
        validateEmail(errors);
        //Validate all required Person fields

        //Make a bunch of private methods for each field
            //What would you do if length was over max
    }

    private void validateFirstName(Person person, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstName", "name.empty");

        String name = person.getFirstName();

        if(!name.trim().matches("[a-zA-Z]+")) {
            errors.rejectValue("firstName", "name.lettersonly");
        }

        if(name.trim().length() == 0) {
            errors.rejectValue("firstName", "name.short");
        }

        if(name.trim().length() > 25) {
            person.setFirstName(name.substring(0, 25));
        }
    }

    private void validateLastName(Person person, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "lastName", "name.empty");

        String name = person.getLastName();

        if(!name.trim().matches("[a-zA-Z]+")) {
            errors.rejectValue("lastName", "name.lettersonly");
        }

        if(name.trim().length() > 25) {
            person.setLastName(name.substring(0, 25));
        }
    }

    private void validateUsername(String username, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "username", "username.empty");

        if(username.trim().length() < 6 || username.trim().length() > 25) {
            errors.rejectValue("username", "username.length");
        }
    }

    private void validateDateOfBirth(String dateOfBirth, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "dateOfBirth", "dateOfBirth.empty");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y", Locale.ENGLISH);

        try {
            LocalDate date = LocalDate.parse(dateOfBirth, formatter);

            if(date.isAfter(LocalDate.now())) {
                errors.rejectValue("dateOfBirth", "date.future");
            }
        } catch(DateTimeParseException e) {
            errors.rejectValue("dateOfBirth", "date.format");
        }
    }

    private void validateSocialSecurityNumber(Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "socialSecurityNumber", "socialSecurityNumber.empty");
    }

    private void validateGender(Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.empty");
    }

    private void validateEmail(Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty");
    }
}
