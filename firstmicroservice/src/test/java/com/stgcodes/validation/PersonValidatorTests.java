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
    private BindingResult bindingResult;
    private ResourceBundleMessageSource messageSource;
    List<String> testCases;
    private Map<String, String> errors;

    @Before
    public void setUp() {
        person = new Person("Bryan", "Byard", "brbyard", "08/13/1978", "123-45-6777", "male", "brbyard@gmail.com");

        personValidator = new PersonValidator();

        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");

        testCases = new ArrayList<>();
        errors = new HashMap<>();
    }

    @Test
    public void testEmptyFirstNameIsInvalid() {
        person.setFirstName("");
        errors = getErrors("firstName", "name.empty");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testFirstNameWithNonLetterCharacterIsInvalid() {
        testCases = Arrays.asList("G3orge", "G/orge", "\\Ge;'[", "   George     *", "  > G \n E \n O \n ");

        for(String testCase : testCases) {
            person.setFirstName(testCase);
            errors = getErrors("firstName", "name.lettersonly");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testFirstNameWithWhitespaceIsTrimmed() {
        testCases = Arrays.asList( "   George     ", "   G \n E \n O \n ");

        for(String testCase : testCases) {
            person.setFirstName(testCase);
            performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }

    @Test
    public void testLongFirstNameIsTruncated() {
        testCases = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ[12", "ABCDE\n   FGH\nIJKLMNOPQRSTU       VWXYZ]]]");

        for(String testCase : testCases) {
            person.setFirstName(testCase);
            performValidation();

            assertEquals(25, person.getFirstName().length());
        }
    }

    @Test
    public void testEmptyLastNameIsInvalid() {
        person.setLastName("");
        errors = getErrors("lastName", "name.empty");

        assertEquals(errors.get("expectedError"), errors.get("actualError"));
    }

    @Test
    public void testLastNameWithNonLetterCharacterIsInvalid() {
        testCases = Arrays.asList("G3orge", "G/orge", "\\Ge;'[", "   George     *", "  > G \n E \n O \n ");

        for(String testCase : testCases) {
            person.setLastName(testCase);
            errors = getErrors("lastName", "name.lettersonly");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    @Test
    public void testLastNameWithWhitespaceIsTrimmed() {
        testCases = Arrays.asList( "   George     ", "   G \n E \n O \n ");

        for(String testCase : testCases) {
            person.setLastName(testCase);
            performValidation();

            assertEquals(0, bindingResult.getErrorCount());
        }
    }

    @Test
    public void testLongLastNameIsTruncated() {
        testCases = Arrays.asList( "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ[12");

        for(String testCase : testCases) {
            person.setLastName(testCase);
            performValidation();

            assertEquals(25, person.getLastName().length());
        }
    }

    @Test
    public void testWrongLengthUsernameIsInvalid() {
        testCases = Arrays.asList("  ", "   Georg", "\n\n\n\n    George w twenty six characters", "\n\n\n\n\n    ");

        for(String testCase : testCases) {
            person.setUsername(testCase);
            errors = getErrors("username", "username.length");

            assertEquals(errors.get("expectedError"), errors.get("actualError"));
        }
    }

    private void performValidation() {
        bindingResult = new BindException(person, "person");
        personValidator.validate(person, bindingResult);
    }

    private Map<String, String> getErrors(String field, String expectedErrorCode) {
        Map<String, String> errors = new HashMap<>();

        performValidation();

        String actualErrorCode = Objects.requireNonNull(bindingResult.getFieldError(field)).getCode();

        errors.put("expectedError", messageSource.getMessage(expectedErrorCode, null, Locale.US));
        errors.put("actualError", messageSource.getMessage(Objects.requireNonNull(actualErrorCode), null, Locale.US));

        return errors;
    }
}