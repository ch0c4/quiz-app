package org.johan.application.exceptions.getQuizzes;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Produces
@Singleton
@Requires(classes = {GetQuizzesBadRequestException.class, ExceptionHandler.class})
public class GetQuizzesBadRequestExceptionHandler implements ExceptionHandler<GetQuizzesBadRequestException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, GetQuizzesBadRequestException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", exception.code);
        body.put("message", exception.message);
        body.put("errors", exception.errors);
        return HttpResponse.badRequest(body);
    }
}
