package org.johan.application.exceptions.startQuiz;

import io.micronaut.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class StartQuizBadRequestException extends Exception {

    public final int code = HttpStatus.BAD_REQUEST.getCode();

    public final String message = "Error on start quiz";

    public final Map<String, List<String>> errors;

    public StartQuizBadRequestException(Map<String, List<String>> errors) {
        super();
        this.errors = errors;
    }
}
