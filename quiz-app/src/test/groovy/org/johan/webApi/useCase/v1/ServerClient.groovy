package org.johan.webApi.useCase.v1

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client
import org.johan.webApi.useCases.v1.answeredQuestion.models.AnsweredQuestionRequest
import org.johan.webApi.useCases.v1.login.models.LoginRequest
import org.johan.webApi.useCases.v1.signUp.models.SignUpRequest
import org.johan.webApi.useCases.v1.startQuiz.models.StartQuizRequest

@Client('/v1')
interface ServerClient {

    @Put("/answers/questions/{questionId}/quizzes/{quizId}/customers/{customerId}")
    HttpResponse<?> answerQuestion(
            Long questionId,
            Long quizId,
            Long customerId,
            @Body AnsweredQuestionRequest body)

    @Get("/quizzes/{quizId}")
    HttpResponse<?> finishQuiz(Long quizId)

    @Get("/quizzes/customers/{customerId}")
    HttpResponse<?> getQuizzes(Long customerId)

    @Post("/customers/login")
    HttpResponse<?> login(@Body LoginRequest body)

    @Post("/customers")
    HttpResponse<?> signup(@Body SignUpRequest body)

    @Post("/quizzes/customers/{customerId}")
    HttpResponse<?> startQuiz(Long customerId, @Body StartQuizRequest body)

}