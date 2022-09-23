package com.stgcodes.validation;

import com.stgcodes.model.Person;
import com.stgcodes.validation.enums.Gender;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Pattern;

@Component
public class PersonValidator implements Validator {

    private final String WHITESPACE_MATCHER = "\\s+";
    private final String LETTER_MATCHER = "[a-zA-Z]+";
    private static final String SOCIAL_SECURITY_MATCHER = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
    private final Integer MAX_NAME_LENGTH = 25;

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
        String name = person.getFirstName().replaceAll(WHITESPACE_MATCHER, "");

        if (!name.matches(LETTER_MATCHER)) {
            errors.rejectValue("firstName", "name.format");
        }

        if (name.length() > MAX_NAME_LENGTH) {
            person.setFirstName(name.substring(0, MAX_NAME_LENGTH));
        }
    }

    private void validateLastName(Person person, Errors errors) {
        String name = person.getLastName().replaceAll(WHITESPACE_MATCHER, "");

        if (!name.matches(LETTER_MATCHER)) {
            errors.rejectValue("lastName", "name.format");
        }

        if (name.length() > MAX_NAME_LENGTH) {
            person.setLastName(name.substring(0, MAX_NAME_LENGTH));
        }
    }

    private void validateUsername(String username, Errors errors) {
        username = username.replaceAll(WHITESPACE_MATCHER, "");

        if (username.length() < 6 || username.length() > MAX_NAME_LENGTH) {
            errors.rejectValue("username", "username.format");
        }
    }

    private void validateDateOfBirth(String dateOfBirth, Errors errors) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y", Locale.ENGLISH);

        try {
            LocalDate date = LocalDate.parse(dateOfBirth, formatter);

            if (date.isAfter(LocalDate.now())) {
                errors.rejectValue("dateOfBirth", "date.future");
            }
        } catch (DateTimeParseException e) {
            errors.rejectValue("dateOfBirth", "date.format");
        }
    }

    private void validateSocialSecurityNumber(String ssn, Errors errors) {
        if (!Pattern.matches(SOCIAL_SECURITY_MATCHER, ssn)) {
            errors.rejectValue("socialSecurityNumber", "ssn.format");
        }
    }

    private void validateGender(String gender, Errors errors) {
        try {
            Gender.valueOf(gender.replaceAll(WHITESPACE_MATCHER, "").toUpperCase());
        } catch (IllegalArgumentException e) {
            errors.rejectValue("gender", "gender.format");
        }
    }

    private void validateEmail(String email, Errors errors) {
        if (!EmailValidator.getInstance().isValid(email)) {
            errors.rejectValue("email", "email.format");
        }
    }
}
