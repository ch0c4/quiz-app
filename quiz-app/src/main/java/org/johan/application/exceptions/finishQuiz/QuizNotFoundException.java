package org.johan.application.exceptions.finishQuiz;

import io.micronaut.http.HttpStatus;

public class QuizNotFoundException extends Exception {

    public final int code = HttpStatus.NOT_FOUND.getCode();

    public final String message = "Quiz not found";

    public QuizNotFoundException() {
        super();
    }
}
