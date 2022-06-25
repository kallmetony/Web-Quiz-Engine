package com.aaronr92.web_quiz_engine.controllers;

import com.aaronr92.web_quiz_engine.security.UserDetailsImpl;
import com.aaronr92.web_quiz_engine.entities.Quiz;
import com.aaronr92.web_quiz_engine.entities.QuizCompleted;
import com.aaronr92.web_quiz_engine.service.QuizCompletedService;
import com.aaronr92.web_quiz_engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    QuizService quizService;

    @Autowired
    QuizCompletedService quizCompletedService;

    // GET req
    @GetMapping("{id}")
    public ResponseEntity<?> getQuiz(@PathVariable long id) {
        if (quizService.findQuizById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quizService.findQuizById(id), HttpStatus.OK);
    }

    @GetMapping
    public Page<Quiz> getQuizzesPage(@RequestParam(defaultValue = "0") int page) {
        return quizService.getPage(page);
    }

    @GetMapping("/completed")
    public Slice<QuizCompleted> getSolvedPage(@RequestParam(defaultValue = "0") int page,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(page);
        return quizCompletedService.getAllCompletedByUser(userDetails.getUsername(), page);
    }

    // POST req
    @PostMapping("/{id}/solve")
    public ResponseEntity<?> checkAnswer(@PathVariable long id,
                                         @RequestBody Map<String, Set<Integer>> answer,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (quizService.findQuizById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (answer.get("answer").containsAll(quizService.findQuizById(id).get().getAnswer()) &&
                answer.get("answer").size() == quizService.findQuizById(id).get().getAnswer().size()) {
            Quiz solved = quizService.findQuizById(id).get();
            quizCompletedService.save(new QuizCompleted(solved.getId(), userDetails.getUsername()));
            return new ResponseEntity<>(Map.of("success", true, "feedback", "Congratulations, you're right!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("success", false, "feedback", "Wrong answer! Please, try again."), HttpStatus.OK);
    }

    @PostMapping
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz,
                        Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        quiz.setEmail(userDetails.getUsername());
        if (quiz.getAnswer() == null) {
            quiz.setAnswer(Set.of());
        }
        quizService.save(quiz);
        return quizService.findQuizById(quiz.getId()).get();
    }

    // DELETE req
    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable long id,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (quizService.findQuizById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (userDetails.getUsername().equals(quizService.findQuizById(id).get().getEmail())) {
            quizService.deleteRecipeById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    // PATCH req
    @PatchMapping("/{id}")
    public void updateQuiz(@RequestBody Quiz quiz,
                           @PathVariable long id,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (quizService.findQuizById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (Objects.equals(quizService.findQuizById(id).get().getEmail(), userDetails.getUsername())) {
            quiz.setId(id);
            quizService.save(quiz);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
