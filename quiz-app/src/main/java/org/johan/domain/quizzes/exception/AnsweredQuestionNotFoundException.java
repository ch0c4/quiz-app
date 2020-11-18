package org.johan.domain.quizzes.exception;

import io.micronaut.http.HttpStatus;

public class AnsweredQuestionNotFoundException extends Exception {

    public final int code = HttpStatus.NOT_FOUND.getCode();

    public final String message = "Customer, quiz or question not found";

    public AnsweredQuestionNotFoundException() {
        super();
    }
}
