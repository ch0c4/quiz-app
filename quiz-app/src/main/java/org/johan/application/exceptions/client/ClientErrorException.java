package org.johan.application.exceptions.client;

import io.micronaut.http.HttpStatus;

public class ClientErrorException extends Exception {
    public final int code = HttpStatus.SERVICE_UNAVAILABLE.getCode();

    public final String message = "Quiz client unavailable";

    public ClientErrorException() {
        super();
    }
}
