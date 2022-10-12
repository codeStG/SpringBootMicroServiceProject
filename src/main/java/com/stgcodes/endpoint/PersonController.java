package com.stgcodes.endpoint;

import com.stgcodes.model.Person;
import com.stgcodes.service.PersonService;
import com.stgcodes.validation.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/people")
@Slf4j
public class PersonController {

    @Autowired
    private PersonService service;

    @Autowired
    private PersonValidator validator;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Person>> getAllPeople() {
        List<Person> people = new ArrayList<>();

        service.findAll().forEach(e -> people.add(service.mapToModel(e)));

        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Person> getPerson(@RequestParam Long personId) {
        Person person = service.mapToModel(service.findById(personId));

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Person cleansedPerson = service.cleanPerson(person);

        BindingResult bindingResult = new BindException(cleansedPerson, "person");
        validator.validate(cleansedPerson, bindingResult);

        if(bindingResult.hasErrors()) {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("ValidationMessages");

            log.error(messageSource.getMessage("person.invalid", null, Locale.US));
            bindingResult.getAllErrors().forEach(e -> log.info(messageSource.getMessage(e, Locale.US)));

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        service.save(service.mapToEntity(person));
        return new ResponseEntity<>(cleansedPerson, HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Person> deletePerson(@RequestParam Long personId) {
        service.delete(personId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}