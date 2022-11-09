package com.stgcodes.endpoint;

import com.stgcodes.criteria.PersonCriteria;
import com.stgcodes.exception.DataAccessException;
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

import java.util.List;

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

    @GetMapping(path = "/search")
    public ResponseEntity<List<Person>> searchForPeople(@RequestBody PersonCriteria criteria) {
        List<Person> people = service.findByCriteria(criteria);
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Person> getPerson(@RequestParam Long personId) throws IdNotFoundException {
        return new ResponseEntity<>(service.findById(personId), HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) throws InvalidRequestBodyException, DataAccessException {
        return new ResponseEntity<>(service.save(person), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person, @RequestParam Long personId) throws InvalidRequestBodyException, DataAccessException {
        return new ResponseEntity<>(service.update(person, personId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Person> deletePerson(@RequestParam Long personId) {
        service.delete(personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}