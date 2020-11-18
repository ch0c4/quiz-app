package org.johan.application.exceptions.finishQuiz;

import io.micronaut.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class FinishQuizBadRequestException extends Exception {

    public final int code = HttpStatus.BAD_REQUEST.getCode();

    public final String message = "Error on fetching quiz";

    public final Map<String, List<String>> errors;

    public FinishQuizBadRequestException(Map<String, List<String>> errors) {
        super();
        this.errors = errors;
    }
}
