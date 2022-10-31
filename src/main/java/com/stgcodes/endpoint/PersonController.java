package com.stgcodes.endpoint;

import com.stgcodes.criteria.PersonCriteria;
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
    public ResponseEntity<Person> getPerson(@RequestParam Long personId) {
        Person person = service.findById(personId);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity <List<Person>> searchForPeople(@RequestBody PersonCriteria criteria) {
        List<Person> people = service.findByCriteria(criteria);

        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Optional<Person> result = service.save(person);

        return result.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person, @RequestParam Long personId) {
        Person refreshedPerson = service.update(person, personId);
        return new ResponseEntity<>(refreshedPerson, HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Person> deletePerson(@RequestParam Long personId) {
        service.delete(personId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}