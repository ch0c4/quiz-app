package org.johan.application.exceptions.client;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Produces
@Singleton
@Requires(classes = {ClientErrorException.class, ExceptionHandler.class})
public class ClientErrorExceptionHandler implements ExceptionHandler<ClientErrorException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, ClientErrorException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", exception.code);
        body.put("message", exception.message);
        return HttpResponse.status(HttpStatus.SERVICE_UNAVAILABLE).body(body);
    }
}
