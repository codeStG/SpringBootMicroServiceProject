package com.stgcodes.validation;

import com.stgcodes.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class PersonValidatorTests {

    private Person person;
    private PersonValidator personValidator;
    private ResourceBundleMessageSource messageSource;

    @Before
    public void setUp() {
        person = new Person("Bryan", "Byard", "brbyard", "08/13/1978", "123-45-6777", "male", "brbyard@gmail.com");

        personValidator = new PersonValidator();

        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
    }

    @Test
    public void testEmptyFirstNameIsInvalid() {
        person.setFirstName("");
        BindingResult bindingResult = new BindException(person, "person");
        personValidator.validate(person, bindingResult);

        String errorCode = Objects.requireNonNull(bindingResult.getFieldError("firstName")).getCode();

        String expectedError = messageSource.getMessage("name.empty", null, Locale.US);
        String actualError = messageSource.getMessage(Objects.requireNonNull(errorCode), null, Locale.US);

        assertEquals(expectedError, actualError);
    }

    @Test
    public void testFirstNameWithNonLetterCharacterIsInvalid() {
        List<String> invalidNames = new ArrayList<>(Arrays.asList("G3orge", "G/orge", "\\Ge;'[", "   George     *", "  > G \n E \n O \n "));

        for(String invalidName : invalidNames) {
            person.setFirstName(invalidName);
            BindingResult bindingResult = new BindException(person, "person");
            personValidator.validate(person, bindingResult);

            String errorCode = Objects.requireNonNull(bindingResult.getFieldError("firstName")).getCode();

            String expectedError = messageSource.getMessage("name.lettersonly", null, Locale.US);
            String actualError = messageSource.getMessage(Objects.requireNonNull(errorCode), null, Locale.US);

            assertEquals(expectedError, actualError);
        }
    }

    @Test
    public void testFirstNameWithWhitespaceIsTrimmed() {
        List<String> validNamesWithWhitespace = new ArrayList<>(Arrays.asList( "   George     ", "   G \n E \n O \n "));

        for(String validName : validNamesWithWhitespace) {
            person.setFirstName(validName);
            BindingResult bindingResult = new BindException(person, "person");
            personValidator.validate(person, bindingResult);

            Integer numOfErrors = bindingResult.getErrorCount();
            Integer expectedNumOfErrors = 0;

            assertEquals(expectedNumOfErrors, numOfErrors);
        }
    }

    @Test
    public void testLongFirstNameIsTruncated() {
        List<String> longNames = new ArrayList<>(Arrays.asList( "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ[12"));

        for(String longName : longNames) {
            person.setFirstName(longName);
            BindingResult bindingResult = new BindException(person, "person");
            personValidator.validate(person, bindingResult);

            Person person = (Person) bindingResult.getTarget();

            String expectedFirstName = longName.substring(0, 25);
            String actualFirstName = person.getFirstName();

            assertEquals(expectedFirstName, actualFirstName);
        }
    }

    @Test
    public void testEmptyLastNameIsInvalid() {
        person.setLastName("");
        BindingResult bindingResult = new BindException(person, "person");
        personValidator.validate(person, bindingResult);

        String errorCode = Objects.requireNonNull(bindingResult.getFieldError("lastName")).getCode();

        String expectedError = messageSource.getMessage("name.empty", null, Locale.US);
        String actualError = messageSource.getMessage(Objects.requireNonNull(errorCode), null, Locale.US);

        assertEquals(expectedError, actualError);
    }

    @Test
    public void testLastNameWithNonLetterCharacterIsInvalid() {
        List<String> invalidNames = new ArrayList<>(Arrays.asList("G3orge", "G/orge", "\\Ge;'[", "   George     *", "  > G \n E \n O \n "));

        for(String invalidName : invalidNames) {
            person.setLastName(invalidName);
            BindingResult bindingResult = new BindException(person, "person");
            personValidator.validate(person, bindingResult);

            String errorCode = Objects.requireNonNull(bindingResult.getFieldError("lastName")).getCode();

            String expectedError = messageSource.getMessage("name.lettersonly", null, Locale.US);
            String actualError = messageSource.getMessage(Objects.requireNonNull(errorCode), null, Locale.US);

            assertEquals(expectedError, actualError);
        }
    }

    @Test
    public void testLastNameWithWhitespaceIsTrimmed() {
        List<String> validNamesWithWhitespace = new ArrayList<>(Arrays.asList( "   George     ", "   G \n E \n O \n "));

        for(String validName : validNamesWithWhitespace) {
            person.setLastName(validName);
            BindingResult bindingResult = new BindException(person, "person");
            personValidator.validate(person, bindingResult);

            Integer numOfErrors = bindingResult.getErrorCount();
            Integer expectedNumOfErrors = 0;

            assertEquals(expectedNumOfErrors, numOfErrors);
        }
    }

    @Test
    public void testLongLastNameIsTruncated() {
        List<String> longNames = new ArrayList<>(Arrays.asList( "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ[12"));

        for(String longName : longNames) {
            person.setLastName(longName);
            BindingResult bindingResult = new BindException(person, "person");
            personValidator.validate(person, bindingResult);

            Person person = (Person) bindingResult.getTarget();

            String expectedFirstName = longName.substring(0, 25);
            String actualFirstName = person.getLastName();

            assertEquals(expectedFirstName, actualFirstName);
        }
    }
}
