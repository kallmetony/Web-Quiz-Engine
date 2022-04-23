package com.aaronr92.web_quiz_engine.Service;

import com.aaronr92.web_quiz_engine.Quiz;
import com.aaronr92.web_quiz_engine.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizRepository repository;

    public Optional<Quiz> findQuizById(long id) {
        return Optional.ofNullable(repository.findQuizById(id));
    }

    public Page<Quiz> getPage(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);

        return repository.findAll(pageable);
    }

    public void save(Quiz toSave) {
        repository.save(toSave);
    }

    public void deleteRecipeById(long id) {
        repository.deleteById(id);
    }
}
