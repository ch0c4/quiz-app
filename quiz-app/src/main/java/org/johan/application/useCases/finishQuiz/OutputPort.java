package org.johan.application.useCases.finishQuiz;

import org.johan.application.exceptions.finishQuiz.FinishQuizBadRequestException;
import org.johan.application.exceptions.finishQuiz.QuizNotCompletedException;
import org.johan.application.exceptions.finishQuiz.QuizNotFoundException;
import org.johan.application.services.Notification;
import org.johan.domain.quizzes.Quiz;

public interface OutputPort {

    void invalid(Notification notification) throws FinishQuizBadRequestException;

    void notFound() throws QuizNotFoundException;

    void ok(Quiz quiz);

    void notCompleted() throws QuizNotCompletedException;
}
