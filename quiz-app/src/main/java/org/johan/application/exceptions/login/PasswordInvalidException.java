package org.johan.application.exceptions.login;

import io.micronaut.http.HttpStatus;

public class PasswordInvalidException extends Exception {

    public final int code = HttpStatus.BAD_REQUEST.getCode();

    public final String message = "Password is invalid";

    public PasswordInvalidException() {
        super();
    }
}
