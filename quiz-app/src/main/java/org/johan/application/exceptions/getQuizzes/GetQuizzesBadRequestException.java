package org.johan.application.exceptions.getQuizzes;

import io.micronaut.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class GetQuizzesBadRequestException extends Exception {

    public final int code = HttpStatus.BAD_REQUEST.getCode();

    public final String message = "Error on get quizzes";

    public final Map<String, List<String>> errors;

    public GetQuizzesBadRequestException(Map<String, List<String>> errors) {
        super();
        this.errors = errors;
    }
}
