package org.johan.application.exceptions.signUp;

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
@Requires(classes = {SignUpBadRequestException.class, ExceptionHandler.class})
public class SignUpBadRequestExceptionHandler implements ExceptionHandler<SignUpBadRequestException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, SignUpBadRequestException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", exception.code);
        body.put("message", exception.message);
        body.put("errors", exception.errors);
        return HttpResponse.badRequest(body);
    }
}
