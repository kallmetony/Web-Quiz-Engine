package com.aaronr92.web_quiz_engine.repository;

import com.aaronr92.web_quiz_engine.entity.SolvedQuiz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvedQuizRepository extends PagingAndSortingRepository<SolvedQuiz, Long> {

    Slice<SolvedQuiz> findAllByUsername(String username, Pageable pageable);
}
