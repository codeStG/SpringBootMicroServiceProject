package com.stgcodes.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stgcodes.criteria.UserCriteria;
import com.stgcodes.exception.DataAccessException;
import com.stgcodes.exceptions.IdNotFoundException;
import com.stgcodes.exceptions.InvalidRequestBodyException;
import com.stgcodes.model.User;
import com.stgcodes.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<User>> searchForUsers(@RequestBody UserCriteria searchCriteria) {
        return new ResponseEntity<>(service.findByCriteria(searchCriteria), HttpStatus.OK);
    }

    @GetMapping(path = "/id")
    public ResponseEntity<User> getUser(@RequestParam Long userId) throws IdNotFoundException {
        return new ResponseEntity<>(service.findById(userId), HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<User> addUser(@RequestBody User user) throws InvalidRequestBodyException, DataAccessException {
        return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<User> updateUser(@RequestBody User user, @RequestParam Long userId) throws InvalidRequestBodyException, IdNotFoundException, DataAccessException {
        return new ResponseEntity<>(service.update(user, userId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<User> deleteUser(@RequestParam Long userId) {
        service.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}