package com.stgcodes.validation;

import com.stgcodes.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class PersonValidatorTests {

    //Unit test each validation method
    Person person;
    PersonValidator validator = new PersonValidator();
    DataBinder dataBinder;
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

    @Before
    public void setUp() {
        person = new Person("Bryan", "Byard", "brbyard", "08/13/1978", "123-45-6777", "male", "brbyard@gmail.com");
        messageSource.setBasename("ValidationMessages");
    }

    @Test
    public void testEmptyFirstNameIsInvalid() {
        person.setFirstName("");
        dataBinder = new DataBinder(person);
        dataBinder.setValidator(validator);
        dataBinder.validate();

        String errorCode = Objects.requireNonNull(dataBinder.getBindingResult().getFieldError("firstName")).getCode();

        String expectedError = messageSource.getMessage("name.empty", null, Locale.US);
        String actualError = messageSource.getMessage(Objects.requireNonNull(errorCode), null, Locale.US);

        assertEquals(expectedError, actualError);
    }

    @Test
    public void testFirstNameWithNonLetterCharacterIsInvalid() {
        List<String> invalidNames = new ArrayList<>(Arrays.asList("G3orge", "G/orge", "\\Ge;'[", "   George     *", "  > G \n E \n O \n "));

        for(String invalidName : invalidNames) {
            person.setFirstName(invalidName);
            dataBinder = new DataBinder(person);
            dataBinder.setValidator(validator);
            dataBinder.validate();

            String errorCode = Objects.requireNonNull(dataBinder.getBindingResult().getFieldError("firstName")).getCode();

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
            dataBinder = new DataBinder(person);
            dataBinder.setValidator(validator);
            dataBinder.validate();

            Integer numOfErrors = dataBinder.getBindingResult().getErrorCount();
            Integer expectedNumOfErrors = 0;

            assertEquals(expectedNumOfErrors, numOfErrors);
        }
    }

    @Test
    public void testLongFirstNameIsTruncated() {
        List<String> longNames = new ArrayList<>(Arrays.asList( "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ[12"));

        for(String longName : longNames) {
            person.setFirstName(longName);
            dataBinder = new DataBinder(person);
            dataBinder.setValidator(validator);
            dataBinder.validate();



            Person person = (Person) dataBinder.getBindingResult().getTarget();

            String expectedFirstName = longName.substring(0, 25);
            String actualFirstName = person.getFirstName();

            assertEquals(expectedFirstName, actualFirstName);
        }
    }


}
