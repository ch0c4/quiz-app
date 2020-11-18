package org.johan.domain.quizzes.exception;

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
@Requires(classes = {AnsweredQuestionNotFoundException.class, ExceptionHandler.class})
public class AnsweredQuestionNotFoundExceptionHandler implements ExceptionHandler<AnsweredQuestionNotFoundException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, AnsweredQuestionNotFoundException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", exception.code);
        body.put("message", exception.message);
        return HttpResponse.notFound(body);
    }
}
