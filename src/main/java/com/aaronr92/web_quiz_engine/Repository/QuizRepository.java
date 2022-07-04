package com.aaronr92.web_quiz_engine.repository;

import com.aaronr92.web_quiz_engine.entity.Quiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {
    @Query
    Quiz findQuizById(Long id);
}
