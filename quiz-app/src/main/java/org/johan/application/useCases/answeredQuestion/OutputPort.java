package org.johan.application.useCases.answeredQuestion;

import org.johan.application.exceptions.answeredQuestion.AnsweredQuestionBadRequestException;
import org.johan.domain.quizzes.exception.AnsweredQuestionNotFoundException;
import org.johan.application.services.Notification;
import org.johan.domain.quizzes.questions.Question;

public interface OutputPort {

    void invalid(Notification notification) throws AnsweredQuestionBadRequestException;

    void notFound() throws AnsweredQuestionNotFoundException;

    void ok(Question question);

    void finishQuiz();
}
