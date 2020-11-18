package org.johan.infrastructure.quizExchange;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

@Client("https://opentdb.com/api.php")
public interface IQuizClient {

    @Get("?amount=10&type=multiple")
    HttpResponse<QuizPackage> fetchQuiz(@QueryValue String category, @QueryValue String difficulty);

}
