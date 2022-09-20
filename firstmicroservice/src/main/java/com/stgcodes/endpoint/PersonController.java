package com.stgcodes.endpoint;

import com.stgcodes.exceptions.InvalidRequestException;
import com.stgcodes.model.Person;
import com.stgcodes.service.PersonService;
import com.stgcodes.validation.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/people")
@Slf4j
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Person>> getAllPeople() {
        return new ResponseEntity<>(personService.getAllPeople(), HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Person> getPerson(@RequestParam Long personId) {
        Person person = personService.getPersonById(personId);

        return person == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        DataBinder dataBinder = new DataBinder(person);
        dataBinder.addValidators(new PersonValidator());
        dataBinder.validate();

        if(dataBinder.getBindingResult().hasErrors()) {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("ValidationMessages");

            log.error(messageSource.getMessage("person.invalid", null, Locale.US));
            dataBinder.getBindingResult().getAllErrors().forEach(e -> log.info(messageSource.getMessage(e, Locale.US)));

            throw new InvalidRequestException();
        }

        return new ResponseEntity<>(personService.addPerson(person), HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Person> deletePerson(@RequestParam Long personId) {

        return new ResponseEntity<>(personService.deletePerson(personId), HttpStatus.OK);
    }
}
