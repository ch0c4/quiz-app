package org.johan.webApi.useCases.v1.finishQuiz;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Status;
import org.johan.application.exceptions.finishQuiz.FinishQuizBadRequestException;
import org.johan.application.exceptions.finishQuiz.QuizNotCompletedException;
import org.johan.application.exceptions.finishQuiz.QuizNotFoundException;
import org.johan.application.services.Notification;
import org.johan.application.useCases.finishQuiz.FinishQuizInput;
import org.johan.application.useCases.finishQuiz.FinishQuizUseCase;
import org.johan.application.useCases.finishQuiz.OutputPort;
import org.johan.domain.quizzes.Quiz;
import org.johan.domain.quizzes.questions.Question;
import org.johan.domain.quizzes.valueObjects.QuestionStatus;
import org.johan.webApi.useCases.v1.finishQuiz.models.FinishQuizQuestionResponse;
import org.johan.webApi.useCases.v1.finishQuiz.models.FinishQuizResponse;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/v1/quizzes")
public class FinishQuizController implements OutputPort {

    @Inject
    private FinishQuizUseCase useCase;

    private FinishQuizResponse finishQuizResponse;

    @Get("/{quizId}")
    @Status(HttpStatus.OK)
    @Error
    public FinishQuizResponse finishQuiz(Long quizId) throws FinishQuizBadRequestException, QuizNotFoundException, QuizNotCompletedException {
        useCase.setOutputPort(this);

        useCase.execute(new FinishQuizInput(quizId));

        return this.finishQuizResponse;
    }

    @Override
    public void invalid(Notification notification) throws FinishQuizBadRequestException {
        throw new FinishQuizBadRequestException(notification.getErrorMessages());
    }

    @Override
    public void notFound() throws QuizNotFoundException {
        throw new QuizNotFoundException();
    }

    @Override
    public void notCompleted() throws QuizNotCompletedException {
        throw new QuizNotCompletedException();
    }

    @Override
    public void ok(Quiz quiz) {
        List<FinishQuizQuestionResponse> questions =  quiz.getQuestions().getQuestions()
                .stream()
                .map(FinishQuizController::mapQuestion)
                .collect(Collectors.toList());
        this.finishQuizResponse = FinishQuizResponse.builder()
                .totalCorrectAnswer(quiz.getTotalCorrectAnswer())
                .questions(questions)
                .timeFinish(quiz.getTimeFinished().getTime())
                .build();
    }

    private static FinishQuizQuestionResponse mapQuestion(Question question) {
        return FinishQuizQuestionResponse.builder()
                .question(question.getSubject().getText())
                .correctAnswers(question.getCorrectAnswer().getText())
                .isCorrect(question.getQuestionStatus().equals(QuestionStatus.ANSWERED_CORRECTLY))
                .build();
    }
}
