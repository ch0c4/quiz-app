package org.johan.webApi.useCases.v1.answeredQuestion;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;
import org.johan.application.exceptions.answeredQuestion.AnsweredQuestionBadRequestException;
import org.johan.domain.quizzes.exception.AnsweredQuestionNotFoundException;
import org.johan.application.services.Notification;
import org.johan.application.useCases.answeredQuestion.AnsweredQuestionInput;
import org.johan.application.useCases.answeredQuestion.AnsweredQuestionUseCase;
import org.johan.application.useCases.answeredQuestion.OutputPort;
import org.johan.domain.quizzes.questions.Question;
import org.johan.domain.quizzes.questions.answers.Answer;
import org.johan.webApi.useCases.v1.answeredQuestion.models.AnsweredQuestionRequest;
import org.johan.webApi.useCases.v1.answeredQuestion.models.AnsweredQuestionResponse;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/v1/answers")
public class AnsweredQuestionController implements OutputPort {

    @Inject
    private AnsweredQuestionUseCase useCase;

    private AnsweredQuestionResponse answeredQuestionResponse;

    @Put("/questions/{questionId}/quizzes/{quizId}/customers/{customerId}")
    @Status(HttpStatus.OK)
    @Error
    public AnsweredQuestionResponse answeredQuestion(
            Long questionId,
            Long quizId,
            Long customerId,
            @Body AnsweredQuestionRequest request) throws AnsweredQuestionBadRequestException, AnsweredQuestionNotFoundException {

        useCase.setOutputPort(this);

        useCase.execute(new AnsweredQuestionInput(customerId, quizId, questionId, request.getAnswer(), request.getAnswerTime()));

        return this.answeredQuestionResponse;
    }

    @Override
    public void invalid(Notification notification) throws AnsweredQuestionBadRequestException {
        throw new AnsweredQuestionBadRequestException(notification.getErrorMessages());
    }

    @Override
    public void notFound() throws AnsweredQuestionNotFoundException {
        throw new AnsweredQuestionNotFoundException();
    }

    @Override
    public void ok(Question question) {
        List<String> answers = question.getAnswers().getAnswers().stream().map(Answer::getText).collect(Collectors.toList());
        Collections.shuffle(answers);

        this.answeredQuestionResponse = AnsweredQuestionResponse.builder()
                .id(question.getQuestionId().getId())
                .question(question.getSubject().getText())
                .answers(question.getAnswers().getAnswers().stream().map(Answer::getText).collect(Collectors.toList()))
                .build();
    }

    @Override
    public void finishQuiz() {
        this.answeredQuestionResponse = AnsweredQuestionResponse.builder()
                .completed(true)
                .build();
    }
}
