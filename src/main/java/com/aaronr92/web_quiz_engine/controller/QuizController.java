package com.aaronr92.web_quiz_engine.controller;

import com.aaronr92.web_quiz_engine.dto.SolvedFeedback;
import com.aaronr92.web_quiz_engine.entity.Quiz;
import com.aaronr92.web_quiz_engine.entity.SolvedQuiz;
import com.aaronr92.web_quiz_engine.security.UserDetailsImpl;
import com.aaronr92.web_quiz_engine.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    // GET req
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable long id) {
        return ResponseEntity.ok(quizService.findQuizById(id));
    }

    @GetMapping("/find/title")
    public ResponseEntity<List<Quiz>> findQuizListByTitle(@RequestParam String title) {
        return ResponseEntity.ok().body(quizService.findQuizListByTitle(title));
    }

    @GetMapping("/find/user")
    public ResponseEntity<List<Quiz>> getQuizListByUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(quizService.findQuizListByUsersEmail(userDetails));
    }

    @GetMapping
    public Page<Quiz> getQuizzesPage(@RequestParam(defaultValue = "0") int page) {
        return quizService.getPage(page);
    }


    @GetMapping("/completed")
    public Slice<SolvedQuiz> getSolvedPage(@RequestParam(defaultValue = "0") int page,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return quizService.getAllCompletedByUser(userDetails.getUsername(), page);
    }

    // POST req
    @PostMapping("/{id}/solve")
    public ResponseEntity<SolvedFeedback> checkAnswer(@PathVariable long id,
                                                      @RequestBody Map<String, Set<Integer>> answer,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(quizService.solveQuiz(id, answer, userDetails));
    }

    @PostMapping("/new")
    public ResponseEntity<Quiz> addQuiz(@Valid @RequestBody Quiz quiz,
                                        Authentication auth) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/quizzes/new")
                .toUriString());
        return ResponseEntity.created(uri)
                .body(quizService.addNewQuiz(quiz, (UserDetailsImpl) auth.getPrincipal()));
    }

    // DELETE req
    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable long id,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        quizService.deleteQuizById(id, userDetails);
    }

    // PATCH req
    @PatchMapping("/{id}")
    public void updateQuiz(@RequestBody Quiz quiz,
                           @PathVariable long id,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        quizService.updateQuiz(id, quiz, userDetails);
    }
}
