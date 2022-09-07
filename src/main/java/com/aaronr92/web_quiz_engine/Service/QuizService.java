package com.aaronr92.web_quiz_engine.service;

import com.aaronr92.web_quiz_engine.dto.SolvedFeedback;
import com.aaronr92.web_quiz_engine.entity.Quiz;
import com.aaronr92.web_quiz_engine.entity.SolvedQuiz;
import com.aaronr92.web_quiz_engine.repository.QuizRepository;
import com.aaronr92.web_quiz_engine.repository.SolvedQuizRepository;
import com.aaronr92.web_quiz_engine.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    public Quiz findQuizById(long id) {
        Quiz quiz = quizRepository.findQuizById(id);
        if (quiz == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return quiz;
    }

    public List<Quiz> findQuizListByTitle(String title) {
        List<Quiz> quizList = quizRepository.findQuizzesByTitle(title);
        if (quizList == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                    "Quiz not found!");
        }
        return quizList;
    }

    public List<Quiz> findQuizListByUsersEmail(UserDetailsImpl userDetails) {
        List<Quiz> quizList = quizRepository.findQuizzesByEmail(userDetails.getUsername());
        if (quizList == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                    "This user does not have his quizzes!");

        return quizList;
    }

    public Page<Quiz> getPage(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        return quizRepository.findAll(pageable);
    }

    public SolvedFeedback solveQuiz(long id, Map<String, Set<Integer>> answer,
                                    UserDetailsImpl userDetails) {
        Quiz quiz = quizRepository.findQuizById(id);
        if (quiz == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (answer.get("answer") == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Answer should be in correct form!");
        if (answer.get("answer").containsAll(quiz.getAnswer()) &&
                answer.get("answer").size() == quiz.getAnswer().size()) {
            solvedQuizRepository.save(new SolvedQuiz(quiz.getId(), userDetails.getUsername()));
            return new SolvedFeedback(SolvedFeedback.FEEDBACK.CORRECT.getMessage(), true);
        }
        return new SolvedFeedback(SolvedFeedback.FEEDBACK.WRONG.getMessage(), false);
    }

    public Slice<SolvedQuiz> getAllCompletedByUser(String username, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        return solvedQuizRepository.findAllByUsername(username, pageable);
    }

    public Quiz addNewQuiz(Quiz quiz, UserDetailsImpl userDetails) {
        quiz.setEmail(userDetails.getUsername());
        if (quiz.getAnswer() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz must contain at least one answer!");
        }
        return quizRepository.save(quiz);
    }

    public void deleteQuizById(long id, UserDetailsImpl userDetails) {
        Quiz quiz = quizRepository.findQuizById(id);
        if (quizRepository.findQuizById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (userDetails.getUsername().equals(quiz.getEmail())) {
            quizRepository.deleteQuizById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    public void updateQuiz(long id, Quiz toSave, UserDetailsImpl userDetails) {
        Quiz retrievedQuiz = quizRepository.findQuizById(id);
        if (retrievedQuiz == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        if (Objects.equals(retrievedQuiz.getEmail(), userDetails.getUsername())) {
            toSave.setId(id);
            quizRepository.save(toSave);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

}
