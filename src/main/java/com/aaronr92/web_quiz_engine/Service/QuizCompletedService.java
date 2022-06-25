package com.aaronr92.web_quiz_engine.service;

import com.aaronr92.web_quiz_engine.entities.QuizCompleted;
import com.aaronr92.web_quiz_engine.repository.QuizCompletedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class QuizCompletedService {
    @Autowired
    QuizCompletedRepository repository;

    public Slice<QuizCompleted> getAllCompletedByUser(String username, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        return repository.findAllByUsername(username, pageable);
    }

    public void save(QuizCompleted toSave) {
        repository.save(toSave);
    }
}
