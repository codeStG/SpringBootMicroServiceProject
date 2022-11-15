package com.stgcodes.validation;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.model.Person;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import static com.stgcodes.utils.constants.CustomMatchers.*;

@Component
@NoArgsConstructor
public class PersonValidator implements Validator {

    private static final Integer MAX_NAME_LENGTH = 25;

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
        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.format");
        validateEmail(person.getEmail(), errors);
        validatePhones(person.getPhones(), errors);
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
