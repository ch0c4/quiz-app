package org.johan.application.exceptions.signUp;

import io.micronaut.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class SignUpBadRequestException extends Exception {

    public final int code = HttpStatus.BAD_REQUEST.getCode();

    public final String message = "Error on created customer";

    public final Map<String, List<String>> errors;

    public SignUpBadRequestException(Map<String, List<String>> errors) {
        super();
        this.errors = errors;
    }
}
