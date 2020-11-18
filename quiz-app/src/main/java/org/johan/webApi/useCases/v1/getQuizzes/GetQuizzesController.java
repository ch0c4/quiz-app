package org.johan.webApi.useCases.v1.getQuizzes;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Status;
import org.johan.application.exceptions.customer.CustomerNotFoundException;
import org.johan.application.exceptions.getQuizzes.GetQuizzesBadRequestException;
import org.johan.application.exceptions.getQuizzes.GetQuizzesNoContentException;
import org.johan.application.services.Notification;
import org.johan.application.useCases.getQuizzes.GetQuizzesInput;
import org.johan.application.useCases.getQuizzes.GetQuizzesUseCase;
import org.johan.application.useCases.getQuizzes.OutputPort;
import org.johan.domain.customers.QuizCollection;
import org.johan.domain.quizzes.Quiz;
import org.johan.webApi.useCases.v1.getQuizzes.models.GetQuizReponse;
import org.johan.webApi.useCases.v1.getQuizzes.models.GetQuizzesResponse;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/v1/quizzes")
public class GetQuizzesController implements OutputPort {

    @Inject
    private GetQuizzesUseCase useCase;

    private GetQuizzesResponse getQuizzesResponse;

    @Get("/customers/{customerId}")
    @Status(HttpStatus.OK)
    @Error
    public GetQuizzesResponse getQuizzes(Long customerId) throws GetQuizzesNoContentException, GetQuizzesBadRequestException, CustomerNotFoundException {
        useCase.setOutputPort(this);

        useCase.execute(new GetQuizzesInput(customerId));

        return this.getQuizzesResponse;
    }

    @Override
    public void invalid(Notification notification) throws GetQuizzesBadRequestException {
        throw new GetQuizzesBadRequestException(notification.getErrorMessages());
    }

    @Override
    public void notFound() throws CustomerNotFoundException {
        throw new CustomerNotFoundException();
    }

    @Override
    public void ok(QuizCollection quizzes) {
        List<GetQuizReponse> getQuizReponses = quizzes.getQuizzes()
                .stream()
                .map(GetQuizzesController::mapQuiz)
                .collect(Collectors.toList());

        this.getQuizzesResponse = GetQuizzesResponse.builder()
                .quizzes(getQuizReponses)
                .build();
    }

    @Override
    public void noContent() throws GetQuizzesNoContentException {
        throw new GetQuizzesNoContentException();
    }

    private static GetQuizReponse mapQuiz(Quiz quiz) {
        return GetQuizReponse.builder()
                .id(quiz.getQuizId().getId())
                .name("Quiz " + quiz.getQuizId().getId())
                .completed(quiz.getCompleted())
                .timeFinished(quiz.getTimeFinished().getTime())
                .build();
    }
}
