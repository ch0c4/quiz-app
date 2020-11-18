package org.johan.application.useCases.startQuiz;

import org.johan.application.exceptions.client.ClientErrorException;
import org.johan.application.exceptions.customer.CustomerNotFoundException;
import org.johan.application.exceptions.startQuiz.StartQuizBadRequestException;
import org.johan.application.services.Notification;
import org.johan.domain.quizzes.Quiz;

public interface OutputPort {

    void ok(Quiz quiz);

    void invalid(Notification notification) throws StartQuizBadRequestException;

    void notFound() throws CustomerNotFoundException;

    void clientError() throws ClientErrorException;
}
