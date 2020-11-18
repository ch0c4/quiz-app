package org.johan.application.exceptions.finishQuiz;

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
@Requires(classes = {QuizNotCompletedException.class, ExceptionHandler.class})
public class QuizNotCompletedExceptionHandler implements ExceptionHandler<QuizNotCompletedException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, QuizNotCompletedException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", exception.code);
        body.put("message", exception.message);
        return HttpResponse.badRequest(body);
    }
}
