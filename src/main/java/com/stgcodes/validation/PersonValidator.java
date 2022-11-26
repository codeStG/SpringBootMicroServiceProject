package com.stgcodes.validation;

import static com.stgcodes.utils.constants.CustomMatchers.LETTER;
import static com.stgcodes.utils.constants.CustomMatchers.SOCIAL_SECURITY;
import static com.stgcodes.utils.constants.CustomMatchers.USERNAME;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.model.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PersonValidator {

    private static final Integer MAX_NAME_LENGTH = 25;

    public void validate(Person person) {
        Errors errors = new BindException(person, "person");

        validateFirstName(person, errors);
        validateLastName(person, errors);
        validateUsername(person.getUsername(), errors);
        validateDateOfBirth(person.getDateOfBirth(), errors);
        validateSocialSecurityNumber(person.getSocialSecurityNumber(), errors);
        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.invalid");
        validateEmail(person.getEmail(), errors);
        validatePhones(person.getPhones(), errors);
        
        if(errors.hasErrors()) {
            log.error(errors.toString());
            throw new InvalidRequestBodyException(Person.class, errors);
        }
    }

    private void validateFirstName(Person person, Errors errors) {
        String firstName = person.getFirstName();

        if (!firstName.matches(LETTER)) {
            errors.rejectValue("firstName", "name.format");
        }

        if (firstName.length() > MAX_NAME_LENGTH) {
            person.setFirstName(firstName.substring(0, MAX_NAME_LENGTH));
        }
    }

    private void validateLastName(Person person, Errors errors) {
        String lastName = person.getLastName();

        if (!lastName.matches(LETTER)) {
            errors.rejectValue("lastName", "name.format");
        }

        if (lastName.length() > MAX_NAME_LENGTH) {
            person.setLastName(lastName.substring(0, MAX_NAME_LENGTH));
        }
    }

    private void validateUsername(String username, Errors errors) {
        if (!username.matches(USERNAME)) {
            errors.rejectValue("username", "username.format");
        }
    }

    private void validateDateOfBirth(LocalDate dateOfBirth, Errors errors) {
        if (dateOfBirth.isAfter(LocalDate.now())) {
            errors.rejectValue("dateOfBirth", "date.future");
        }
    }

    private void validateSocialSecurityNumber(String ssn, Errors errors) {
        if(!Pattern.matches(SOCIAL_SECURITY, ssn)) {
            errors.rejectValue("socialSecurityNumber", "ssn.format");
        }
    }

    private void validateEmail(String email, Errors errors) {
        if (!EmailValidator.getInstance().isValid(email)) {
            errors.rejectValue("email", "email.format");
        }
    }
    
    private void validatePhones(List<PhoneEntity> phones, Errors errors) {
        if (phones.isEmpty()) {
            errors.rejectValue("phones", "phones.size");
        }
    }
}
