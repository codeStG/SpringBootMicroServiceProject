package com.stgcodes.validation;

import com.stgcodes.model.Person;
import com.stgcodes.validation.enums.Gender;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Pattern;

@Component
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
        validateSocialSecurityNumber(person.getSocialSecurityNumber(), errors);
        validateGender(person.getGender(), errors);
        validateEmail(person.getEmail(), errors);
    }

    private void validateFirstName(Person person, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstName", "name.empty");

        String name = person.getFirstName().replaceAll("\\s+","");

        if(!name.matches("[a-zA-Z]+") && name.trim().length() > 0) {
            errors.rejectValue("firstName", "name.lettersonly");
        }

        if(name.length() > 25) {
            person.setFirstName(name.substring(0, 25));
        }
    }

    private void validateLastName(Person person, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "lastName", "name.empty");

        String name = person.getLastName().replaceAll("\\s+","");

        if(!name.matches("[a-zA-Z]+")) {
            errors.rejectValue("lastName", "name.lettersonly");
        }

        if(name.length() > 25) {
            person.setLastName(name.substring(0, 25));
        }
    }

    private void validateUsername(String username, Errors errors) {
        username = username.replaceAll("\\s+","");

        if(username.length() < 6 || username.length() > 25) {
            errors.rejectValue("username", "username.length");
        }
    }

    private void validateDateOfBirth(String dateOfBirth, Errors errors) {
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

    private void validateSocialSecurityNumber(String ssn, Errors errors) {
        if(!Pattern.matches("^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$", ssn)) {
            errors.rejectValue("socialSecurityNumber", "ssn.format");
        }
    }

    private void validateGender(String gender, Errors errors) {
        try {
            Gender.valueOf(gender.replaceAll("\\s+","").toUpperCase());
        } catch(IllegalArgumentException e) {
            errors.rejectValue("gender", "gender.invalid");
        }
    }

    private void validateEmail(String email, Errors errors) {
        if(!EmailValidator.getInstance().isValid(email)) {
            errors.rejectValue("email", "email.format");
        }
    }
}
