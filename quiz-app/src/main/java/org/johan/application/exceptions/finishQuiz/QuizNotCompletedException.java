package org.johan.application.exceptions.finishQuiz;

import io.micronaut.http.HttpStatus;

public class QuizNotCompletedException extends Exception {

    public final int code = HttpStatus.BAD_REQUEST.getCode();

    public final String message = "Quiz not completed";

    public QuizNotCompletedException() {
        super();
    }
}
