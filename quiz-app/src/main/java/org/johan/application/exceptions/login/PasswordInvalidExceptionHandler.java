package org.johan.application.exceptions.login;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Produces
@Singleton
@Requires(classes = {PasswordInvalidException.class, ExceptionHandler.class})
public class PasswordInvalidExceptionHandler implements ExceptionHandler<PasswordInvalidException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, PasswordInvalidException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", exception.code);
        body.put("message", exception.message);
        return HttpResponse.badRequest(body);
    }
}
