package com.aaronr92.web_quiz_engine.repository;

import com.aaronr92.web_quiz_engine.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByEmail(String email);
}
