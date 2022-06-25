package com.aaronr92.web_quiz_engine.controllers;

import com.aaronr92.web_quiz_engine.entities.User;
import com.aaronr92.web_quiz_engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {
    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping
    public void register(@Valid @RequestBody User user) {
        if (repository.findUserByEmail(user.getEmail()) == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRole("ROLE_USER");
            repository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already registered");
        }
    }
}
