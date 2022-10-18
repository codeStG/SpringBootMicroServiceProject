package com.stgcodes.endpoint;

import com.stgcodes.model.Person;
import com.stgcodes.service.PersonService;
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
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<Person> getPerson(@RequestParam Long personId) {
        Person person = service.findById(personId);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        service.save(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person, @RequestParam Long personId) {
        service.update(person, personId);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Person> deletePerson(@RequestParam Long personId) {
        service.delete(personId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}