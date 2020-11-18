package org.johan.application.useCases.finishQuiz;

import org.johan.application.exceptions.finishQuiz.FinishQuizBadRequestException;
import org.johan.application.exceptions.finishQuiz.QuizNotCompletedException;
import org.johan.application.exceptions.finishQuiz.QuizNotFoundException;
import org.johan.domain.quizzes.IQuizRepository;
import org.johan.domain.quizzes.Quiz;
import org.johan.domain.quizzes.valueObjects.QuizId;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FinishQuizUseCase {

    @Inject
    private IQuizRepository quizRepository;

    private OutputPort outputPort;

    public void setOutputPort(OutputPort outputPort) {
        this.outputPort = outputPort;
    }

    public void execute(FinishQuizInput input) throws FinishQuizBadRequestException, QuizNotFoundException, QuizNotCompletedException {
        if (input.getModelState().isValid()) {
            finishQuizInternal(input.getQuizId());
        } else {
            this.outputPort.invalid(input.getModelState());
        }
    }

    private void finishQuizInternal(QuizId quizId) throws QuizNotFoundException, QuizNotCompletedException {
        Quiz existingQuiz = quizRepository.findQuiz(quizId);

        if (existingQuiz == null) {
            this.outputPort.notFound();
            return;
        }

        if(!existingQuiz.getCompleted()) {
            this.outputPort.notCompleted();
        }

        this.outputPort.ok(existingQuiz);
    }
}
