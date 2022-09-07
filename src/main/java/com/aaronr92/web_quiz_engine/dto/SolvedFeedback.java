package com.aaronr92.web_quiz_engine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class SolvedFeedback {

    private String feedback;
    private boolean success;

    @Getter
    @AllArgsConstructor
    public enum FEEDBACK {
        CORRECT("Congratulations, you're right!"),
        WRONG("Wrong answer! Please, try again.");

        private final String message;
    }

}
