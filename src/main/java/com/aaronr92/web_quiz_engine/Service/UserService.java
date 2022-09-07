package com.aaronr92.web_quiz_engine.service;

import com.aaronr92.web_quiz_engine.dto.UserDTO;
import com.aaronr92.web_quiz_engine.entity.User;
import com.aaronr92.web_quiz_engine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public void signup(User user) {
        if (userRepository.findUserByEmail(user.getEmail()) == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRole("ROLE_USER");
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already registered");
        }
    }

    public void login(UserDTO user) {
        User retrievedUser = userRepository.findUserByEmail(user.getEmail());
        if (retrievedUser == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "User not found!");
        if (!encoder.matches(user.getPassword(), retrievedUser.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
