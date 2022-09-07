package com.aaronr92.web_quiz_engine.controller;

import com.aaronr92.web_quiz_engine.dto.UserDTO;
import com.aaronr92.web_quiz_engine.entity.User;
import com.aaronr92.web_quiz_engine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void register(@Valid @RequestBody User user) {
        userService.signup(user);
    }

    @PostMapping("/authenticate")
    ResponseEntity<Void> login(@RequestBody UserDTO user) {
        userService.login(user);
        return ResponseEntity.ok().build();
    }
}
