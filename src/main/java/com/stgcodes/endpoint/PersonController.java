package com.stgcodes.endpoint;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.model.Person;
import com.stgcodes.service.PersonService;
import com.stgcodes.utils.sorting.PersonComparator;
import com.stgcodes.validation.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

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
        List<Person> people = service.findAll();
        people.sort(new PersonComparator());

        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity getPerson(@RequestParam Long personId) {
        Person person;

        try {
            person = service.findById(personId);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<Person>> searchForPeople(@RequestBody PersonCriteria criteria) {
        List<Person> people = service.findByCriteria(criteria);
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity addPerson(@RequestBody Person person) {
        HttpStatus status = HttpStatus.OK;
        Person result;

        try{
            result = service.save(person);
        } catch(InvalidRequestBodyException | PersistenceException e) {
            status = e.getClass().equals(PersistenceException.class) ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(e.getMessage(), status);
        }

        return new ResponseEntity<>(result, status);
    }

    @PutMapping(path = "/update")
    public ResponseEntity updatePerson(@RequestBody Person person, @RequestParam Long personId) {
        HttpStatus status = HttpStatus.OK;
        Person result;

        try {
            result = service.update(person, personId);
        } catch(IdNotFoundException | PersistenceException e) {
            status = e.getClass().equals(PersistenceException.class) ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(e.getMessage(), status);
        }

        return new ResponseEntity<>(result, status);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Person> deletePerson(@RequestParam Long personId) {
        service.delete(personId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}