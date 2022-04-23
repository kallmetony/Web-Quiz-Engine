package com.aaronr92.web_quiz_engine.Repository;

import com.aaronr92.web_quiz_engine.Quiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {
    @Query
    Quiz findQuizById(Long id);
}
