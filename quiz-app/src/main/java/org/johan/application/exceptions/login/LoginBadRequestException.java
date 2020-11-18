package org.johan.application.exceptions.login;

import io.micronaut.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class LoginBadRequestException extends Exception {

    public final int code = HttpStatus.BAD_REQUEST.getCode();

    public final String message = "Error on login";

    public final Map<String, List<String>> errors;

    public LoginBadRequestException(Map<String, List<String>> errors) {
        super();
        this.errors = errors;
    }

}
