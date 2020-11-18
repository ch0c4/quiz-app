package org.johan.application.exceptions.customer;

import io.micronaut.http.HttpStatus;

public class CustomerAlreadyExistException extends Exception {

    public final int code = HttpStatus.BAD_REQUEST.getCode();

    public final String message = "Customer already exist";

    public CustomerAlreadyExistException() {
        super();
    }
}
