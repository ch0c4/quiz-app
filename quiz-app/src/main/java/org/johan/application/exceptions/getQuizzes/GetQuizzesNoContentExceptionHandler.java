package org.johan.application.exceptions.getQuizzes;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {GetQuizzesNoContentException.class, ExceptionHandler.class})
public class GetQuizzesNoContentExceptionHandler implements ExceptionHandler<GetQuizzesNoContentException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, GetQuizzesNoContentException exception) {
        return HttpResponse.noContent();
    }
}
