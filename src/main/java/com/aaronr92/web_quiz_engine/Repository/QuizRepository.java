package com.aaronr92.web_quiz_engine.repository;

import com.aaronr92.web_quiz_engine.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Quiz findQuizById(long id);

    List<Quiz> findQuizzesByTitle(String title);

    List<Quiz> findQuizzesByEmail(String email);

    void deleteQuizById(long id);
}
