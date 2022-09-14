package com.stgcodes.endpoint;

import com.stgcodes.model.Person;
import com.stgcodes.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<Person> addPerson(@Valid @RequestBody Person person) {
        return new ResponseEntity<>(personService.addPerson(person), HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<Person> deletePerson(@RequestParam Long personId) {

        return new ResponseEntity<>(personService.deletePerson(personId), HttpStatus.OK);
    }
}
