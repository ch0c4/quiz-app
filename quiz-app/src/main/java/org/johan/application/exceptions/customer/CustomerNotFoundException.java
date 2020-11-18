package org.johan.application.exceptions.customer;

import io.micronaut.http.HttpStatus;

public class CustomerNotFoundException extends Exception {

    public final int code = HttpStatus.NOT_FOUND.getCode();

    public final String message = "Customer not found";

    public CustomerNotFoundException() {
        super();
    }
}
