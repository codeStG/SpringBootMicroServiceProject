package com.stgcodes.endpoint;

import com.stgcodes.dao.PersonDao;
import com.stgcodes.entity.PersonEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    @Autowired
    PersonDao dao;

    @GetMapping(path = "/all")
    public ResponseEntity<List<PersonEntity>> getPeople() {
        return new ResponseEntity<>(dao.getPeople(), HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<List<PersonEntity>> getPerson(@RequestParam Long personId) {
        return new ResponseEntity<>(dao.getPersonbyId(personId), HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<PersonEntity> addPerson(@RequestBody PersonEntity personEntity) {
        return new ResponseEntity<>(dao.addPerson(personEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public HttpStatus deletePerson(@RequestParam Long personId) {
        dao.deletePersonById(personId);

        return HttpStatus.OK;
    }
}
