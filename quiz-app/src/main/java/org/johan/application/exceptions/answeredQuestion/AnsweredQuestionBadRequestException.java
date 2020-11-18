package org.johan.application.exceptions.answeredQuestion;

import io.micronaut.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class AnsweredQuestionBadRequestException extends Exception {
    public final int code = HttpStatus.BAD_REQUEST.getCode();

    public final String message = "Error on answering question";

    public final Map<String, List<String>> errors;

    public AnsweredQuestionBadRequestException(Map<String, List<String>> errors) {
        super();
        this.errors = errors;
    }
}
