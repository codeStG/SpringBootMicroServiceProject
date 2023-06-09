package com.stgcodes.validation;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import static com.stgcodes.utils.constants.CustomMatchers.*;

@Slf4j
@Component
public class UserValidator {

    private static final Integer MAX_NAME_LENGTH = 25;

    public void validate(User user) {
        Errors errors = new BindException(user, "user");

        validateFirstName(user, errors);
        validateLastName(user, errors);
        validateUsername(user.getUsername(), errors);
        validateDateOfBirth(user.getDateOfBirth(), errors);
        validateSocialSecurityNumber(user.getSocialSecurityNumber(), errors);
        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.invalid");
        validateEmail(user.getEmail(), errors);
        validatePhones(user.getPhones(), errors);
        
        if(errors.hasErrors()) {
            log.error(errors.toString());
            throw new InvalidRequestBodyException(User.class, errors);
        }
    }

    private void validateFirstName(User user, Errors errors) {
        String firstName = user.getFirstName();

        if (!firstName.matches(LETTER)) {
            errors.rejectValue("firstName", "name.format");
        }

        if (firstName.length() > MAX_NAME_LENGTH) {
            user.setFirstName(firstName.substring(0, MAX_NAME_LENGTH));
        }
    }

    private void validateLastName(User user, Errors errors) {
        String lastName = user.getLastName();

        if (!lastName.matches(LETTER)) {
            errors.rejectValue("lastName", "name.format");
        }

        if (lastName.length() > MAX_NAME_LENGTH) {
            user.setLastName(lastName.substring(0, MAX_NAME_LENGTH));
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
