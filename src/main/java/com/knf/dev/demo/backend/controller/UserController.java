package com.knf.dev.demo.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knf.dev.demo.backend.entity.User;
import com.knf.dev.demo.backend.exception.InternalServerError;
import com.knf.dev.demo.backend.exception.UserNotFound;
import com.knf.dev.demo.backend.repository.UserRepository;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        try {
            List<User> users = new ArrayList<User>();
            userRepository.findAll().forEach(users::add);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable("id") Long id) {

        Optional<User> userdata = userRepository.findById(id);
        if (userdata.isPresent()) {
            return new ResponseEntity<>(userdata.get(), HttpStatus.OK);
        } else {
            throw new UserNotFound("Invalid User Id");
        }

    }
}
