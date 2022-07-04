package com.aaronr92.web_quiz_engine.repository;

import com.aaronr92.web_quiz_engine.entity.QuizCompleted;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCompletedRepository extends PagingAndSortingRepository<QuizCompleted, Long> {
    @Query
    Slice<QuizCompleted> findAllByUsername(String username, Pageable pageable);
}
