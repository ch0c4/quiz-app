package org.johan.application.useCases.getQuizzes;

import org.johan.application.exceptions.customer.CustomerNotFoundException;
import org.johan.application.exceptions.getQuizzes.GetQuizzesBadRequestException;
import org.johan.application.exceptions.getQuizzes.GetQuizzesNoContentException;
import org.johan.application.services.Notification;
import org.johan.domain.customers.QuizCollection;

public interface OutputPort {

    void invalid(Notification notification) throws GetQuizzesBadRequestException;

    void notFound() throws CustomerNotFoundException;

    void ok(QuizCollection quizzes);

    void noContent() throws GetQuizzesNoContentException;

}
