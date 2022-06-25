package com.aaronr92.web_quiz_engine.security;

import com.aaronr92.web_quiz_engine.entities.User;
import com.aaronr92.web_quiz_engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email + " not found");
        }
        return new UserDetailsImpl(user);
    }
}
