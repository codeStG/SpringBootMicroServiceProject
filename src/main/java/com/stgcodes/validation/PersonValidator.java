package com.stgcodes.validation;

import com.stgcodes.model.Person;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static com.stgcodes.utils.constants.CustomMatchers.LETTER;
import static com.stgcodes.utils.constants.CustomMatchers.SOCIAL_SECURITY;

@Component
public class PersonValidator implements Validator {

    private final Integer MAX_NAME_LENGTH = 25;

    public PersonValidator() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
    }

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
        validateEmail(person.getEmail(), errors);
    }

    private void validateFirstName(Person person, Errors errors) {
        String firstName = person.getFirstName();

        if (!firstName.matches(LETTER)) {
            errors.rejectValue("firstName", "name.format", new Object[]{firstName}, "Invalid name format");
        }

        if (firstName.length() > MAX_NAME_LENGTH) {
            person.setFirstName(firstName.substring(0, MAX_NAME_LENGTH));
        }
    }

    private void validateLastName(Person person, Errors errors) {
        String lastName = person.getLastName();

        if (!lastName.matches(LETTER)) {
            errors.rejectValue("lastName", "name.format", new Object[]{lastName}, "Invalid name format");
        }

        if (lastName.length() > MAX_NAME_LENGTH) {
            person.setLastName(lastName.substring(0, MAX_NAME_LENGTH));
        }
    }

    private void validateUsername(String username, Errors errors) {
        if (username.length() < 6 || username.length() > MAX_NAME_LENGTH) {
            errors.rejectValue("username", "username.format", new Object[]{username}, "Invalid username format");
        }
    }

    private void validateDateOfBirth(LocalDate dateOfBirth, Errors errors) {
        if (dateOfBirth.isAfter(LocalDate.now())) {
            errors.rejectValue("dateOfBirth", "date.future", new Object[]{dateOfBirth}, "Date cannot be in the future");
        }
    }

    private void validateSocialSecurityNumber(String ssn, Errors errors) {
        if(!Pattern.matches(SOCIAL_SECURITY, ssn)) {
            errors.rejectValue("socialSecurityNumber", "ssn.format", new Object[]{ssn}, "Invalid social security format");
        }
    }

    private void validateEmail(String email, Errors errors) {
        if (!EmailValidator.getInstance().isValid(email)) {
            errors.rejectValue("email", "email.format", new Object[]{email}, "Invalid email format");
        }
    }
}
