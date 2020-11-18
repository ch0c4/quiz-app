package org.johan.webApi.useCases.v1.startQuiz;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import org.johan.application.exceptions.client.ClientErrorException;
import org.johan.application.exceptions.customer.CustomerNotFoundException;
import org.johan.application.exceptions.startQuiz.StartQuizBadRequestException;
import org.johan.application.services.Notification;
import org.johan.application.useCases.startQuiz.OutputPort;
import org.johan.application.useCases.startQuiz.StartQuizInput;
import org.johan.application.useCases.startQuiz.StartQuizUseCase;
import org.johan.domain.quizzes.Quiz;
import org.johan.domain.quizzes.questions.Question;
import org.johan.domain.quizzes.questions.answers.Answer;
import org.johan.webApi.useCases.v1.startQuiz.models.StartQuizRequest;
import org.johan.webApi.useCases.v1.startQuiz.models.StartQuizResponse;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/v1/quizzes")
public class StartQuizController implements OutputPort {

    @Inject
    private StartQuizUseCase useCase;

    private StartQuizResponse startQuizResponse;

    @Post("/customers/{customerId}")
    @Status(HttpStatus.OK)
    @Error
    public StartQuizResponse startQuiz(Long customerId, @Body StartQuizRequest request) throws ClientErrorException, StartQuizBadRequestException, CustomerNotFoundException {
        useCase.setOutputPort(this);

        useCase.execute(new StartQuizInput(customerId, request.getCategory(), request.getDifficulty()));

        return startQuizResponse;
    }

    @Override
    public void ok(Quiz quiz) {
        Question question = quiz.getNextQuestion();

        List<String> answers = question.getAnswers().getAnswers().stream().map(Answer::getText).collect(Collectors.toList());
        Collections.shuffle(answers);

        this.startQuizResponse = StartQuizResponse.builder()
                .quizId(quiz.getQuizId().getId())
                .questionId(question.getQuestionId().getId())
                .question(question.getSubject().getText())
                .answers(answers)
                .build();
    }

    @Override
    public void invalid(Notification notification) throws StartQuizBadRequestException {
        throw new StartQuizBadRequestException(notification.getErrorMessages());
    }

    @Override
    public void notFound() throws CustomerNotFoundException {
        throw new CustomerNotFoundException();
    }

    @Override
    public void clientError() throws ClientErrorException {
        throw new ClientErrorException();
    }
}
