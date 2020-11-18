package org.johan.application.exceptions.customer;

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
@Requires(classes = {CustomerNotFoundException.class, ExceptionHandler.class})
public class CustomerNotFoundExceptionHandler implements ExceptionHandler<CustomerNotFoundException, HttpResponse<?>> {
    @Override
    public HttpResponse<?> handle(HttpRequest request, CustomerNotFoundException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", exception.code);
        body.put("message", exception.message);
        return HttpResponse.notFound(body);
    }
}
